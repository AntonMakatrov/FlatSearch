FROM eclipse-temurin:19-jre-focal
MAINTAINER ITAcademy

ADD ./target/mail-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/mail-service.jar"]

EXPOSE 8080 8089
