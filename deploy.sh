#scp target/simple-service-0.0.1-SNAPSHOT.war root@47.98.176.148:/server/running
mvn tomcat7:redeploy -DskipTests -Pprod
