pipeline {

    agent any

    environment {
        APP_NAME = "todo-webapp"
        VERSION  = "1.0.${BUILD_NUMBER}"

        SONAR_URL = "http://sonarqube:9000"
        NEXUS_URL = "http://nexus:8081"

        IMAGE = "charantejafk/todo-webapp"
    }

    stages {

        /* ------------------------ CHECKOUT ------------------------ */
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Charantej-afk/To-do-list.git', branch: 'main'
            }
        }

        /* ------------------------ BUILD WAR ------------------------ */
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

                    script {
                        WAR_FILE = sh(script: "ls target/*.war", returnStdout: true).trim()
                        echo "Detected WAR File: ${WAR_FILE}"
                    }

                    sh """
                        echo "Uploading WAR to Nexus..."
                        curl -v -u $NEXUS_USER:$NEXUS_PSW \
                        --upload-file ${WAR_FILE} \
                        ${NEXUS_URL}/repository/maven-releases/com/app/${APP_NAME}/${VERSION}/${APP_NAME}-${VERSION}.war
                    """
                }
            }
        }

        /* ------------------------ NEXUS DOWNLOAD ------------------------ */
        stage('Download WAR for Docker') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'NEXUS_CRED',
                                                 usernameVariable: 'NEXUS_USER',
                                                 passwordVariable: 'NEXUS_PSW')]) {

                    sh """
                        curl -u $NEXUS_USER:$NEXUS_PSW \
                        -o ${APP_NAME}.war \
                        ${NEXUS_URL}/repository/maven-releases/com/app/${APP_NAME}/${VERSION}/${APP_NAME}-${VERSION}.war
                    """
                }
            }
        }

        /* ------------------------ DOCKER IMAGE ------------------------ */
        stage('Build Docker Image') {
            steps {
                sh """
                    docker build -t ${IMAGE}:${VERSION} .
                    docker tag ${IMAGE}:${VERSION} ${IMAGE}:latest
                """
            }
        }

        /* ------------------------ DOCKER PUSH ------------------------ */
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

        /* ------------------------ DEPLOY ------------------------ */
        stage('Deploy WAR on Tomcat') {
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
            echo "🎉 To-Do WebApp WAR CI/CD Pipeline Completed Successfully!"
        }
        failure {
            echo "❌ Pipeline Failed!"
        }
    }
}
