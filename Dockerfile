# Use uma imagem base oficial do Maven com JDK 17
FROM maven:3.8.4-openjdk-17-slim AS build

# Define o diretório de trabalho no container
WORKDIR /app

# Copia o arquivo pom.xml para baixar as dependências
COPY pom.xml .

# Baixa as dependências offline (otimização de cache)
RUN mvn dependency:go-offline -B

# Copia o código fonte do projeto
COPY src ./src

# Comando padrão para executar os testes
# Usa o perfil 'ci' ou configurações padrão. Assume que o navegador será remoto ou headless.
CMD ["mvn", "test", "-Dheadless=true"]
