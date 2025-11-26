# ========= ETAPA 1: BUILD =========
FROM gradle:8.10.2-jdk17-alpine AS build

WORKDIR /app

COPY . .

RUN gradle clean bootJar --no-daemon

# ========= ETAPA 2: RUNTIME =========
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Render usa la variable de entorno PORT
ENV PORT=8080

EXPOSE 8080

COPY --from=build /app/build/libs/*.jar app.jar

# -Dserver.port=${PORT} para que respete el puerto de Render
ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","app.jar"]
