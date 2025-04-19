
FROM openjdk:11.0-jre-buster

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY mall-service.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]