FROM openjdk:8-jre

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS="-server -Xms2048M -Xmn768M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/server/logs/`date`.hprof \
-Djava.rmi.server.hostname=47.98.176.148 -Dcom.sun.management.jmxremote.port=10000 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

ADD *.war /app.war

EXPOSE 8443 10000
CMD echo "The application will start in ${JHIPSTER_SLEEP}s..." && \
    sleep ${JHIPSTER_SLEEP} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.war
