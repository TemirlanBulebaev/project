FROM openjdk:16-alpine3.13

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

EXPOSE 8082

COPY src ./src

CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql"]