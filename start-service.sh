#nohup java -jar ./target/simple-service-0.0.1-SNAPSHOT.war &

#build the package
mvn clean install -Pprod -DskipTests

#kill the services
pkill "simple-service-0.0.1-SNAPSHOT.war"

#sleep 5 seconds
sleep 5

#startup the service
nohup java -jar ./target/simple-service-0.0.1-SNAPSHOT.war \
-server -Xms2048M -Xmn768M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/server/logs/`date`.hprof \
-Djava.rmi.server.hostname=47.98.176.148 -Dcom.sun.management.jmxremote.port=10000 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false &

#docker-compose up -d
