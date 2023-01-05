FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD target/cutter1d-0.0.2-SNAPSHOT.jar cutter1d-0.0.2-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/cutter1d-0.0.2-SNAPSHOT.jar"]