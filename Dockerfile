FROM gradle:7.5.1-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:11.0.1-jdk
ENV BOT_NAME=
ENV BOT_TOKEN=
ENV KEY=
ENV SECRET=
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/TelegramBot-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
