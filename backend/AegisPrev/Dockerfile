# ---- Etapa 1: build ----
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copia primeiro só os arquivos do Maven Wrapper e o pom.xml
# para aproveitar cache de dependências entre builds
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Agora copia o restante do código e builda o jar (pulando os testes)
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ---- Etapa 2: imagem final, só com o runtime ----
FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
