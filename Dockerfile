# Estágio 1: Build - Usa uma imagem completa do Maven para compilar o projeto.
FROM maven:3.8.5-openjdk-17 AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia o pom.xml e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código-fonte
COPY src ./src

# Compila e empacota a aplicação
RUN mvn package -DskipTests

# Estágio 2: Run - Usa uma imagem Java super leve, apenas para executar.
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copia apenas o arquivo .jar compilado do estágio anterior
COPY --from=builder /app/target/*.jar app.jar

# ALTERAÇÃO AQUI: Expõe a porta 8080
EXPOSE 8080

# Comando para iniciar a API quando o contêiner subir
CMD ["java", "-jar", "app.jar"]