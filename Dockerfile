FROM openjdk:17-alpine
WORKDIR /opt
EXPOSE 8081
COPY target/*.jar /opt/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
