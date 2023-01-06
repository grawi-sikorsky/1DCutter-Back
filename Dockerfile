#build
# FROM maven:3.6.3 AS build
# COPY src /home/app/src
# COPY pom.xml /home/app
# RUN mvn -f /home/app/pom.xml package -DskipTests

FROM maven:3.6.3 as build
# Copy only pom.xml of your projects and download dependencies
COPY pom.xml .
RUN mvn -B -f pom.xml dependency:go-offline
# Copy all other project files and build project
COPY . .
RUN mvn -B install -DskipTests

# Run stage
FROM openjdk:17
COPY --from=build ./target/*.jar ./
# COPY --from=build /app/target/cutter1d-0.0.2-SNAPSHOT.jar /app/cutter1d-0.0.2-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cutter1d-0.0.2-SNAPSHOT.jar"]


# #pack
# FROM openjdk:17
# VOLUME /tmp
# EXPOSE 8080
# ADD target/cutter1d-0.0.2-SNAPSHOT.jar cutter1d-0.0.2-SNAPSHOT.jar
# ENTRYPOINT ["java","-jar","/cutter1d-0.0.2-SNAPSHOT.jar"]