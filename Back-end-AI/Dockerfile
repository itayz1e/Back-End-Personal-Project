FROM openjdk:17
COPY target/back-end-AI-0.0.1-SNAPSHOT.jar /usr/src/back-end-ai.jar
COPY src/main/resources/application.properties /opt/conf/application.properties
CMD ["java", "-jar", "/usr/src/back-end-ai.jar", "--spring.config.location=file:/opt/conf/application.properties"]

