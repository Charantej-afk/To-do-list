# Simple Tomcat deployment
FROM tomcat:10.1.18-jre17

# Remove default apps to avoid conflicts
RUN rm -rf /usr/local/tomcat/webapps/ROOT
RUN rm -rf /usr/local/tomcat/webapps/docs
RUN rm -rf /usr/local/tomcat/webapps/examples

# Copy the WAR file (built by Maven)
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
