FROM openjdk:16-alpine
WORKDIR /
COPY target/ladi-work-service-0.0.1-SNAPSHOT.jar ladi-work-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","ladi-work-service-0.0.1-SNAPSHOT.jar"]
