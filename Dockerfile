FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B
COPY src/ src/
RUN ./mvnw package -DskipTests -B
RUN java -Djarmode=tools -jar target/*.jar extract --destination extracted

FROM eclipse-temurin:17-jre
WORKDIR /app
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
COPY --from=builder /app/extracted/ ./
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "order-service-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "application.jar"]