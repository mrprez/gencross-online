FROM tomcat:8.5.69-jdk8-openjdk

RUN apt-get update && apt-get install gettext-base
RUN apt-get install -y netcat

COPY target/gencross-online-*.war /usr/local/tomcat/webapps/
RUN mv /usr/local/tomcat/webapps/gencross-online-*.war /usr/local/tomcat/webapps/ROOT.war

COPY docker/context.template.xml /usr/local/tomcat/conf

COPY docker/mysql-connector-java-8.0.26.jar /usr/local/tomcat/lib

RUN apt-get install -y dos2unix
COPY docker/entrypoint.sh /usr/local
RUN dos2unix /usr/local/entrypoint.sh
RUN apt-get --purge remove -y dos2unix

RUN rm -rf /var/lib/apt/lists/*


ENTRYPOINT [ "/bin/sh", "/usr/local/entrypoint.sh" ]
CMD [ "/usr/local/tomcat/bin/catalina.sh", "run" ]
