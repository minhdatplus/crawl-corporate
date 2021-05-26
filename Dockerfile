FROM maven:3.6.3-jdk-11-slim AS build
COPY . /app
RUN mvn -f /app/pom.xml clean package

FROM openjdk:8-jdk-alpine
EXPOSE 8080/tcp
RUN  apk update && apk upgrade && apk add netcat-openbsd
WORKDIR corporate-action-service
COPY --from=build /app/target/corporate-action-service.jar corporate-action-service.jar
RUN chmod -R 777 corporate-action-service.jar
ADD run.sh .
RUN chmod +x run.sh
CMD ./run.sh