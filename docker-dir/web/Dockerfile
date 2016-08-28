FROM tomcat:9.0
ENV CATALINA_OPTS="-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n"
ADD applets-java-api.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]