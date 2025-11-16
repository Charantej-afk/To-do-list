FROM tomcat:10.1.18-jre17

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy WAR file
COPY ROOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
