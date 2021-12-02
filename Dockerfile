FROM openjdk:11
COPY /target/tenpo-interview-0.0.1-SNAPSHOT.jar tenpo-interview-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/tenpo-interview-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080