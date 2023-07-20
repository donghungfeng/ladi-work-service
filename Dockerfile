FROM openjdk:16-alpine
WORKDIR /
COPY target/ladi-0.0.1-SNAPSHOT.jar ladi.jar
EXPOSE 8080
CMD ["java","-jar","ladi.jar"]
