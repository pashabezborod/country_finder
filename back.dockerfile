FROM gradle:8.5.0-jdk21 as BUILD
RUN mkdir -p /app
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=BUILD /app/build/libs/country_finder-0.0.1-SNAPSHOT.jar finder.jar
ENTRYPOINT ["java", "-jar", "finder.jar"]