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
- **Allure Framework 2.24.0** - Geração de relatórios de testes
- **SLF4J + Logback** - Logging
- **GitHub Actions** - CI/CD Pipeline

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

## CI/CD Pipeline

O projeto possui um pipeline de CI/CD configurado com **GitHub Actions** que executa automaticamente verificações de código, testes e geração de relatórios Allure.

### O que o Pipeline faz?

O pipeline é executado automaticamente nas seguintes situações:
- **Push** para as branches `main`, `master` ou `develop`
- **Pull Requests** para as branches `main`, `master` ou `develop`
- **Execução manual** via GitHub Actions (workflow_dispatch)

### Estrutura do Pipeline

O pipeline é composto por 4 jobs principais que executam em sequência:

#### 1. 🔍 Verificação de Código (`code-quality`)
- **Duração:** ~5-10 minutos
- **Objetivo:** Validar a qualidade e compilação do código
- **Ações:**
  - Compila o projeto Java
  - Verifica formatação de código (quando configurado)
  - Utiliza cache do Maven para acelerar a execução

#### 2. 🧪 Execução de Testes (`tests`)
- **Duração:** ~15-30 minutos
- **Objetivo:** Executar todos os testes automatizados
- **Estratégia:** Execução em paralelo com 3 matrizes:
  - **`api`**: Executa apenas testes de API (RestAssured)
  - **`ui`**: Executa apenas testes de UI (Selenium)
  - **`all`**: Executa todos os testes (API + UI)
- **Configurações:**
  - Modo headless ativado automaticamente
  - Display virtual (Xvfb) configurado para testes UI
  - Chrome com opções otimizadas para CI (`--no-sandbox`, `--disable-dev-shm-usage`)
  - Cache do Maven para dependências
- **Saídas:**
  - Relatórios Allure individuais por suite de testes
  - Resultados dos testes (XML e TXT)
  - Artefatos publicados para download

#### 3. 📊 Geração de Relatório Consolidado (`generate-allure-report`)
- **Duração:** ~5-10 minutos
- **Objetivo:** Consolidar todos os resultados e gerar relatório Allure final
- **Ações:**
  - Baixa todos os resultados de testes dos jobs anteriores
  - Gera relatório Allure consolidado com todos os testes
  - Publica relatório como artefato para download
  - Comenta em Pull Requests com link para o relatório

#### 4. 📋 Resumo da Execução (`summary`)
- **Duração:** ~1-2 minutos
- **Objetivo:** Exibir resumo visual da execução no GitHub
- **Informações:**
  - Status de cada job
  - Links para artefatos
  - Instruções para visualizar relatórios

### Como Acessar os Relatórios Allure

1. **Via GitHub Actions:**
   - Acesse a aba **"Actions"** no repositório
   - Selecione a execução do workflow desejada
   - Role até a seção **"Artifacts"**
   - Baixe o artefato **"allure-report-consolidado"**
   - Extraia o arquivo ZIP
   - Abra o arquivo `index.html` no navegador

2. **Via Pull Request:**
   - O pipeline automaticamente comenta no PR quando o relatório é gerado
   - Siga as instruções no comentário para baixar o relatório

### Otimizações do Pipeline

- ✅ **Cache do Maven:** Dependências são cacheadas entre execuções, reduzindo tempo de build
- ✅ **Execução Paralela:** Testes executam em paralelo usando matriz strategy
- ✅ **Fail-Fast Desabilitado:** Um teste falhando não interrompe os demais
- ✅ **Timeouts Configurados:** Previne execuções infinitas
- ✅ **Continue-on-Error:** Pipeline continua mesmo com falhas parciais
- ✅ **Artefatos Retidos:** Relatórios disponíveis por 30 dias, resultados por 7 dias

### Executando Testes Localmente com Allure

Para gerar relatórios Allure localmente:

```bash
# Executar testes e gerar resultados
mvn clean test

# Gerar relatório Allure
mvn allure:report

# Abrir relatório no navegador (Linux/Mac)
mvn allure:serve

# Windows: Abra manualmente target/site/allure-maven-plugin/index.html
```

### Configurações do CI

O pipeline utiliza automaticamente as seguintes configurações:
- **Java 11** (Temurin distribution)
- **Modo Headless:** `headless=true` (via system property)
- **Navegador:** Chrome (configurado automaticamente)
- **Display Virtual:** Xvfb para testes UI sem interface gráfica

## Notas Importantes

- **URL da aplicação:** https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
- **Credenciais padrão:** Admin / admin123 (configuráveis via `config.properties`)
- **Endpoint API:** `/api/login` (configurável via `config.properties`)
- **Tempo máximo de resposta:** 5 segundos (configurável via `config.properties`)
- **Navegadores suportados:** Chrome (padrão), Firefox, Edge
- O projeto utiliza **WebDriverManager** para gerenciamento automático dos drivers dos navegadores
- Após login bem-sucedido, o OrangeHRM redireciona para a página de dashboard (`/web/index.php/dashboard`)
- Testes de **SQL Injection e XSS** são recomendados para execução manual com ferramentas especializadas
- **CI/CD:** Pipeline configurado com GitHub Actions e relatórios Allure automáticos