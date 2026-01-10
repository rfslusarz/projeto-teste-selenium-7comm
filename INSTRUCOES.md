# Instruções de Execução - OrangeHRM Automation

## Sobre o Projeto

Este projeto automatiza testes de UI para o site OrangeHRM (https://opensource-demo.orangehrmlive.com/), uma aplicação de gerenciamento de recursos humanos. Os testes validam funcionalidades de login, incluindo cenários positivos, negativos e validações de performance.

## Instalação das Ferramentas

### 1. Instalar Java JDK 11 ou superior

#### Windows:
1. Baixe o JDK em: https://www.oracle.com/java/technologies/downloads/
   - Ou use OpenJDK: https://adoptium.net/
2. Execute o instalador
3. Configure as variáveis de ambiente:
   - Adicione `C:\Program Files\Java\jdk-11\bin` ao PATH
   - OU configure a variável `JAVA_HOME` apontando para a pasta do JDK

**Verificar instalação:**
```bash
java -version
```

### 2. Instalar Maven

#### Windows:
1. Baixe o Maven em: https://maven.apache.org/download.cgi
2. Extraia o arquivo em uma pasta (ex: `C:\Program Files\Apache\maven`)
3. Configure as variáveis de ambiente:
   - Adicione `C:\Program Files\Apache\maven\bin` ao PATH
   - Configure a variável `MAVEN_HOME` apontando para a pasta do Maven

**Verificar instalação:**
```bash
mvn -version
```

### 3. Instalar Navegador Chrome
- Certifique-se de que o Google Chrome está instalado
- O WebDriverManager baixará automaticamente o driver correto

## Executando os Testes

Após instalar Java, Maven e configurar as variáveis de ambiente:

### 1. Compilar o projeto
```bash
mvn clean compile
```

### 2. Executar testes de UI (OrangeHRM)
```bash
mvn test -Dtest=LoginUITests
```

**Resultado esperado:**
```
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### 3. Executar todos os testes (UI + API exemplo)
```bash
mvn test
```

### 4. Executar apenas testes de API (ReqRes - API externa de exemplo)
```bash
mvn test -Dtest=LoginAPITests
```

### 5. Executar teste específico
```bash
# Login válido
mvn test -Dtest=LoginUITests#testLoginValido

# Redirecionamento após login
mvn test -Dtest=LoginUITests#testRedirecionamentoAposLogin

# Validação de tempo de carregamento
mvn test -Dtest=LoginUITests#testTempoCarregamentoPaginaLogin
```

## Configuração

### Arquivo config.properties

O arquivo `src/main/resources/config.properties` contém todas as configurações:

```properties
# OrangeHRM Configuration
base.url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
valid.username=Admin
valid.password=admin123
browser=chrome
headless=false
implicit.wait=10
explicit.wait=20

# API Configuration (ReqRes - API Externa de Exemplo)
api.base.url=https://reqres.in
api.timeout=30000
api.max.response.time=5000
```

### Configuração do Navegador

Por padrão, o projeto está configurado para usar Chrome. Para alterar:

1. Edite o arquivo `src/main/resources/config.properties`
2. Altere a propriedade `browser` para: `chrome`, `firefox` ou `edge`
3. Para executar em modo headless (sem interface gráfica), altere `headless=true`

### Credenciais do OrangeHRM

- **Username:** Admin
- **Password:** admin123
- **URL:** https://opensource-demo.orangehrmlive.com/web/index.php/auth/login

## Estrutura do Projeto

- **Page Objects**: `src/main/java/com/orangehrm/pages/LoginPage.java`
- **Utilitários**: 
  - `src/main/java/com/orangehrm/utils/ConfigReader.java`
  - `src/main/java/com/orangehrm/utils/DriverManager.java`
- **Testes UI**: `src/test/java/com/orangehrm/ui/LoginUITests.java`
- **Testes API**: `src/test/java/com/orangehrm/api/LoginAPITests.java` (ReqRes - exemplo)
- **Classe Base**: `src/test/java/com/orangehrm/base/TestBase.java`
- **Configurações**: `src/main/resources/config.properties`
- **Documentação**: 
  - `README.md` - Visão geral do projeto
  - `GUIA-INSTALACAO-E-EXECUCAO.md` - Guia completo de instalação
  - `cenarios-de-teste.md` - Cenários de teste documentados

## Testes Implementados

### ✅ Testes de UI (OrangeHRM) - 9 testes automatizados

1. **testLoginValido** - Login válido com validação de redirecionamento e tempo (≤5s)
2. **testLoginInvalidoUsuarioESenhaInvalidos** - Credenciais inválidas
3. **testLoginInvalidoUsuarioValidoSenhaInvalida** - Usuário válido, senha inválida
4. **testLoginInvalidoUsuarioInvalidoSenhaValida** - Usuário inválido, senha válida
5. **testLoginUsuarioEmBrancoSenhaValida** - Username em branco
6. **testLoginUsuarioValidoSenhaEmBranco** - Senha em branco
7. **testLoginUsuarioESenhaEmBranco** - Ambos os campos em branco
8. **testTempoCarregamentoPaginaLogin** - Performance (≤5s)
9. **testRedirecionamentoAposLogin** - Validação de redirecionamento para dashboard

**Status:** ✅ Todos os 9 testes passaram com sucesso

## Notas Importantes

- Os drivers dos navegadores são gerenciados automaticamente pelo WebDriverManager
- As credenciais de teste podem ser alteradas no arquivo `config.properties`
- Os testes de API usam a API pública do ReqRes (https://reqres.in) como exemplo
- O OrangeHRM demo pode ter instabilidade ocasional
- Após login bem-sucedido, o OrangeHRM redireciona para `/web/index.php/dashboard`
