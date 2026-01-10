# Guia de Instalação e Execução - OrangeHRM Automation

## Status Atual da Verificação

- **Aplicação:** OrangeHRM (https://opensource-demo.orangehrmlive.com/)
- **Estrutura de arquivos:** Completa e correta
- **Credenciais padrão:** Admin / admin123

## Instalação do Ambiente

### 1. Instalar Java JDK 11 ou superior

#### Windows:
1. Baixe o JDK em: https://www.oracle.com/java/technologies/downloads/
   - Ou use OpenJDK: https://adoptium.net/
2. Execute o instalador
3. Adicione ao PATH do sistema:
   - Adicione `C:\Program Files\Java\jdk-11\bin` (ou caminho da instalação)
   - Ou configure a variável `JAVA_HOME`

**Verificar instalação:**
```bash
java -version
```

### 2. Instalar Maven

#### Windows:
1. Baixe o Maven em: https://maven.apache.org/download.cgi
2. Extraia em uma pasta (ex: `C:\Program Files\Apache\maven`)
3. Adicione ao PATH:
   - Adicione `C:\Program Files\Apache\maven\bin` ao PATH
   - Configure a variável `MAVEN_HOME` apontando para a pasta do Maven

**Verificar instalação:**
```bash
mvn -version
```

### 3. Verificar Configuração

Execute o script de verificação:
```powershell
powershell -ExecutionPolicy Bypass -File verificar-teste.ps1
```

## Executando os Testes

### Compilar o Projeto
```bash
mvn clean compile
```

### Executar Todos os Testes
```bash
mvn test
```

### Executar Apenas Testes de UI
```bash
mvn test -Dtest=LoginUITests
```

### Executar Apenas Testes de API (ReqRes - API Externa de Exemplo)
```bash
mvn test -Dtest=LoginAPITests
```

### Executar Teste Específico
```bash
# Teste de login válido (UI)
mvn test -Dtest=LoginUITests#testLoginValido

# Teste de redirecionamento (UI)
mvn test -Dtest=LoginUITests#testRedirecionamentoAposLogin

# Teste de API ReqRes - Status 200 (exemplo)
mvn test -Dtest=LoginAPITests#testAPILoginStatus200
```

## Testes Disponíveis

### Testes de UI (Selenium) - OrangeHRM
Todos os 9 testes foram executados com sucesso na aplicação OrangeHRM:
- `testLoginValido` - Login válido (Admin/admin123) com validação de redirecionamento e tempo (≤5s)
- `testLoginInvalidoUsuarioESenhaInvalidos` - Login com credenciais inválidas
- `testLoginInvalidoUsuarioValidoSenhaInvalida` - Usuário válido, senha inválida
- `testLoginInvalidoUsuarioInvalidoSenhaValida` - Usuário inválido, senha válida
- `testLoginUsuarioEmBrancoSenhaValida` - Username em branco, senha válida
- `testLoginUsuarioValidoSenhaEmBranco` - Username válido, senha em branco
- `testLoginUsuarioESenhaEmBranco` - Ambos os campos em branco
- `testTempoCarregamentoPaginaLogin` - Validação de performance (≤5s)
- `testRedirecionamentoAposLogin` - Validação de redirecionamento para dashboard

**Resultado:** Tests run: 9, Failures: 0, Errors: 0, Skipped: 0

### Testes de API (RestAssured) - ReqRes (API Externa de Exemplo)
**Nota:** Estes testes usam a API pública do ReqRes (https://reqres.in) como exemplo para validação de endpoints REST. Não estão relacionados ao OrangeHRM.
- `testAPILoginStatus200` - Login válido retorna token
- `testAPILoginStatus400UsuarioNaoEncontrado` - Usuário não encontrado
- `testAPILoginStatus400SenhaIncorreta` - Senha incorreta
- `testAPILoginSemPassword` - Requisição sem campo password
- `testAPILoginSemEmail` - Requisição sem campo email
- `testAPISemDados` - Requisição sem dados
- `testAPITempoResposta` - Validação de tempo de resposta (≤5s)
- `testAPIRegisterUsuario` - Registro de novo usuário

## Configuração

Edite o arquivo `src/main/resources/config.properties` para ajustar:

```properties
# OrangeHRM Configuration
base.url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
valid.username=Admin
valid.password=admin123
browser=chrome
headless=false
implicit.wait=10
explicit.wait=20

# API Configuration - ReqRes (API Externa de Exemplo)
api.base.url=https://reqres.in
api.timeout=30000
api.max.response.time=5000
```

## Solução de Problemas

### Erro: "Java não encontrado"
- Verifique se o Java está instalado: `java -version`
- Adicione o Java ao PATH do sistema
- Configure a variável `JAVA_HOME`

### Erro: "Maven não encontrado"
- Verifique se o Maven está instalado: `mvn -version`
- Adicione o Maven ao PATH do sistema
- Configure a variável `MAVEN_HOME`

### Erro: "Driver do navegador não encontrado"
- O WebDriverManager baixa automaticamente os drivers
- Verifique sua conexão com a internet
- Para Chrome, certifique-se de que o Chrome está instalado

### Erro: "Timeout na conexão"
- Verifique sua conexão com a internet
- Verifique se o site https://opensource-demo.orangehrmlive.com/ está acessível
- Ajuste os tempos de espera no `config.properties`
- O OrangeHRM pode ter instabilidade ocasional na versão demo

## Estrutura de Saída dos Testes

Após executar os testes de UI com `mvn test -Dtest=LoginUITests`, você verá:

```
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

Os relatórios detalhados estarão em:
- `target/surefire-reports/` - Relatórios de execução
- `target/surefire-reports/TEST-*.xml` - Relatórios XML

## Testes Executados com Sucesso

**Testes de UI (OrangeHRM):** 9/9 passaram
- Login válido com credenciais Admin/admin123
- Validação de redirecionamento para dashboard
- Validação de tempos de carregamento (≤5s)
- Todos os cenários negativos validados
- Validação de campos em branco

**Testes de API (ReqRes):** Todos os testes de exemplo passaram

## Próximos Passos

1. Instale Java JDK 11+ (já instalado)
2. Instale Maven (já instalado)
3. Execute `mvn clean compile` para compilar o projeto
4. Execute `mvn test -Dtest=LoginUITests` para executar os testes de UI do OrangeHRM
5. Verifique os relatórios em `target/surefire-reports/`
