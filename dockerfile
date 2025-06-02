# 1) Usa imagem Maven com JDK 17, nomeia etapa como 'builder'
FROM maven:3.9.6-eclipse-temurin-17 AS build

# 2) Define a pasta de trabalho dentro do container
WORKDIR /app

# 3) copia o pom.xml para essa pasta
COPY . .

# 4) Roda o build do Maven, gera o .jar
RUN mvn clean package -DskipTests

# 5) Imagem final menor só com Java Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 6) copia o .jar gerado na etapa builder
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# 7) comando para rodar o app quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]
