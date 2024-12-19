# Use uma imagem base do Java
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR gerado pelo Maven/Gradle para o container
COPY target/*.jar app.jar

# Exponha a porta que a aplicação utiliza
EXPOSE 8082

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]