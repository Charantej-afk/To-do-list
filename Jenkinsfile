pipeline {

    agent any

    environment {
        // App metadata
        APP_NAME    = "ecommerce-app"
        VERSION     = "1.0.${BUILD_NUMBER}"

        // Internal service URLs (running in Docker network)
        SONAR_URL   = "http://sonarqube:9000"
        NEXUS_URL   = "http://nexus:8081"

        // Docker Hub repo
        IMAGE       = "charantejafk/ecommerce-app"
    }

    stages {

        /* ------------------------ CHECKOUT ------------------------ */
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Charantej-afk/E-commeres-repo.git', branch: 'main'
            }
        }

        /* ------------------------ BUILD ------------------------ */
        stage('Build WAR') {
            steps {
                sh """
                    mvn clean package -DskipTests
                    ls -l target
                """
            }
        }

        /* ------------------------ SONARQUBE ------------------------ */
        stage('SonarQube Scan') {
            steps {
                withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv('My SonarQube Server') {
                        sh """
                            mvn sonar:sonar \
                                -Dsonar.host.url=${SONAR_URL} \
                                -Dsonar.login=$SONAR_TOKEN
                        """
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        /* ------------------------ NEXUS UPLOAD ------------------------ */
        stage('Upload WAR to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'NEXUS_CRED',
                                                  usernameVariable: 'NEXUS_USER',
                                                  passwordVariable: 'NEXUS_PSW')]) {
                    sh """
                        echo "Uploading WAR to Nexus..."
                        curl -v -u $NEXUS_USER:$NEXUS_PSW \
                        --upload-file target/${APP_NAME}.war \
                        ${NEXUS_URL}/repository/maven-releases/com/ecommerce/${APP_NAME}/${VERSION}/${APP_NAME}-${VERSION}.war
                    """
                }
            }
        }

        /* ------------------------ DOWNLOAD BACK FROM NEXUS ------------------------ */
        stage('Download WAR from Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'NEXUS_CRED',
                                                  usernameVariable: 'NEXUS_USER',
                                                  passwordVariable: 'NEXUS_PSW')]) {
                    sh """
                        rm -f ${APP_NAME}.war || true
                        echo "Downloading WAR from Nexus..."
                        curl -u $NEXUS_USER:$NEXUS_PSW \
                        -o ${APP_NAME}.war \
                        ${NEXUS_URL}/repository/maven-releases/com/ecommerce/${APP_NAME}/${VERSION}/${APP_NAME}-${VERSION}.war
                    """
                }
            }
        }

        /* ------------------------ DOCKER BUILD ------------------------ */
        stage('Build Docker Image') {
            steps {
                sh """
                    docker build -t ${IMAGE}:${VERSION} .
                    docker tag ${IMAGE}:${VERSION} ${IMAGE}:latest
                """
            }
        }

        /* ------------------------ DOCKER HUB PUSH ------------------------ */
        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB',
                                                  usernameVariable: 'DHUB_USER',
                                                  passwordVariable: 'DHUB_PASS')]) {

                    sh """
                        echo $DHUB_PASS | docker login -u $DHUB_USER --password-stdin
                        docker push ${IMAGE}:${VERSION}
                        docker push ${IMAGE}:latest
                    """
                }
            }
        }

        /* ------------------------ DEPLOY TO TOMCAT (DOCKER) ------------------------ */
        stage('Deploy Application') {
            steps {
                sh """
                    docker rm -f ${APP_NAME} || true
                    docker run -d --name ${APP_NAME} -p 8080:8080 ${IMAGE}:latest
                """
            }
        }
    }

    post {
        success {
            echo "🎉 Pipeline executed successfully!"
        }
        failure {
            echo "❌ Pipeline failed!"
        }
    }
}
