# OrangeHRM - Automação de Testes

Projeto de automação de testes para o site OrangeHRM (https://opensource-demo.orangehrmlive.com/) utilizando Selenium WebDriver e RestAssured.

## Estrutura do Projeto

```
desafio-teste-7comm/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── orangehrm/
│   │   │           ├── pages/
│   │   │           │   └── LoginPage.java          # Page Object para página de login
│   │   │           ├── utils/
│   │   │           │   ├── ConfigReader.java       # Leitor de propriedades
│   │   │           │   └── DriverManager.java      # Gerenciador do WebDriver
│   │   │           └── models/                     # Modelos de dados (reservado)
│   │   └── resources/
│   │       └── config.properties                   # Configurações do projeto
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── bugbank/
│       │           ├── api/
│       │           │   └── LoginAPITests.java      # Testes de API com RestAssured
│       │           ├── ui/
│       │           │   └── LoginUITests.java       # Testes de UI com Selenium
│       │           ├── base/
│       │           │   └── TestBase.java           # Classe base para testes
│       │           └── helpers/                    # Helpers de teste (reservado)
│       └── resources/
│           ├── test-data/                          # Dados de teste (reservado)
│           └── features/                           # Cucumber BDD (opcional)
├── pom.xml                                          # Configuração Maven
├── cenarios-de-teste.md                            # Documentação de cenários (14 cenários)
├── README.md                                        # Este arquivo
└── .gitignore                                       # Arquivos ignorados pelo Git
```

## Tecnologias Utilizadas

- **Java 11**
- **Maven** - Gerenciamento de dependências
- **Selenium WebDriver 4.15.0** - Automação de interface web
- **WebDriverManager 5.6.2** - Gerenciamento automático de drivers
- **RestAssured 5.3.2** - Testes de API
- **JUnit 5.10.0** - Framework de testes
- **SLF4J + Logback** - Logging

## Pré-requisitos

- Java JDK 11 ou superior
- Maven 3.6 ou superior
- Navegador Chrome, Firefox ou Edge instalado

## Configuração

1. Clone o repositório
2. Verifique se o Java e Maven estão instalados:
   ```bash
   java -version
   mvn -version
   ```

3. Configure as propriedades no arquivo `src/main/resources/config.properties`:

```properties
base.url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
valid.username=Admin
valid.password=admin123
browser=chrome
headless=false
```

## Executando os Testes

### Executar todos os testes
```bash
mvn test
```

### Executar apenas testes de UI
```bash
mvn test -Dtest=com.orangehrm.ui.LoginUITests
```

### Executar apenas testes de API
```bash
mvn test -Dtest=LoginAPITests
```

### Executar teste específico
```bash
mvn test -Dtest=LoginUITests#testLoginValido
```

## Cenários de Teste Implementados

**Total:** 14 cenários documentados (ver `cenarios-de-teste.md` para detalhes completos)

### Testes de UI (Selenium) - 11 cenários automatizados

**Funcionais Positivos:**
1. **Login válido - Perfil Admin** - Validação de redirecionamento e tempo de carregamento (≤5s)
2. **Redirecionamento após login válido** - Validação de URL e tempo de resposta (≤5s)
3. **Tempo de carregamento da página** - Validação de performance (≤5s)

**Funcionais Negativos:**
4. **Login inválido - usuário e senha inválidos** - Validação de mensagem de erro
5. **Login inválido - usuário válido e senha inválida** - Validação de mensagem específica
6. **Login inválido - usuário inválido e senha válida** - Validação de segurança
7. **Login em branco - usuário em branco e senha válida** - Validação de campo obrigatório
8. **Login em branco - usuário válido e senha em branco** - Validação de campo obrigatório
9. **Login em branco - usuário e senha em branco** - Validação múltipla de campos

### Testes de API (RestAssured) - Endpoint `/api/login`

1. **Status 200** - Login válido retorna token e perfil do usuário
   - Validação de token de autenticação (JWT)
   - Validação de objeto de perfil contendo informações do usuário
   - Tempo de resposta ≤ 5 segundos

2. **Status 401** - Credenciais inválidas
   - Validação de mensagem de erro apropriada

3. **Status 403** - Acesso negado
   - Validação de mensagem de erro para acesso negado

4. **Status 423** - Usuário bloqueado após múltiplas tentativas
   - Validação de bloqueio após 5 tentativas falhas
   - Mensagem de bloqueio apropriada

5. **Requisição sem dados** - Campos vazios
   - Validação de tratamento de requisição inválida

6. **Tempo de resposta da API** - Validação de performance (≤5s)

### Testes Manuais (2 cenários)

1. **SQL Injection** - Segurança (teste manual recomendado)
2. **XSS (Cross-Site Scripting)** - Segurança (teste manual recomendado)

## Padrões e Boas Práticas Utilizadas

- **Page Object Pattern (POM)** - Separação de lógica de teste e elementos da página
- **WebDriverWait** - Esperas explícitas configuráveis (até 20 segundos) para evitar falhas por timing
- **Propriedades Configuráveis** - Configurações externas via arquivo `config.properties`
- **DriverManager com ThreadLocal** - Suporte a execução paralela de testes
- **JUnit 5** - Framework moderno de testes com anotações e display names
- **RestAssured** - Framework para testes de API REST com validações de status code e response body
- **Validação de Performance** - Tempos de carregamento e resposta validados (máximo 5 segundos)
- **Validação de Segurança** - Testes de bloqueio, credenciais inválidas e proteção contra ataques

## Estrutura das Classes

### LoginPage
Classe Page Object que encapsula todos os elementos e ações da página de login, utilizando WebDriverWait para esperas explícitas.

### DriverManager
Gerencia a criação e destruição do WebDriver, suportando múltiplos navegadores (Chrome, Firefox, Edge) com configuração via properties.

### ConfigReader
Classe utilitária para leitura das propriedades de configuração do arquivo `config.properties`.

### TestBase
Classe base que inicializa o driver antes de cada teste e faz cleanup após a execução.

## Requisitos Atendidos

- **14+ cenários de teste documentados** (funcionais positivos, negativos e segurança)
- **Classificação dos tipos de teste** (FT, FN, SEG, PERF)
- **Matriz de automação** definindo camadas UI, API e Manual
- **Estrutura de pastas** organizada para boa compreensão do projeto
- **Endpoint /api/login** validado com RestAssured
- **Status codes validados:** 200 (token e perfil), 401, 403, 423
- **Perfis de usuário** considerados (Admin e Usuário Comum)
- **Redirecionamentos** validados após login válido (redirecionamento para `/dashboard` após autenticação bem-sucedida)
- **Mensagens de erro** validadas para todos os cenários negativos
- **Tempos de carregamento** validados (máximo 5 segundos)
- **Validação de token e perfil** no status 200 da API
- **Estrutura proposta** documentada no arquivo de cenários

## Notas Importantes

- **URL da aplicação:** https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
- **Credenciais padrão:** Admin / admin123 (configuráveis via `config.properties`)
- **Endpoint API:** `/api/login` (configurável via `config.properties`)
- **Tempo máximo de resposta:** 5 segundos (configurável via `config.properties`)
- **Navegadores suportados:** Chrome (padrão), Firefox, Edge
- O projeto utiliza **WebDriverManager** para gerenciamento automático dos drivers dos navegadores
- Após login bem-sucedido, o OrangeHRM redireciona para a página de dashboard (`/web/index.php/dashboard`)
- Testes de **SQL Injection e XSS** são recomendados para execução manual com ferramentas especializadas
