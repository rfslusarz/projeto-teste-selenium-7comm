# Automação de Testes - Selenium 7comm

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4.15.0-43B02A?style=for-the-badge&logo=selenium&logoColor=white)
![RestAssured](https://img.shields.io/badge/Rest_Assured-5.3.2-005C84?style=for-the-badge&logo=checkmarx&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit-5.10.0-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Allure Report](https://img.shields.io/badge/Allure_Report-2.24.0-FF7F00?style=for-the-badge&logo=allure&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-CI%2FCD-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)

Projeto de automação de testes para o site OrangeHRM (https://opensource-demo.orangehrmlive.com/) utilizando Selenium WebDriver, RestAssured e Docker.

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
│   │   │           └── models/                     # Modelos de dados
│   │   └── resources/
│   │       └── config.properties                   # Configurações do projeto
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── orangehrm/
│       │           ├── api/
│       │           │   └── LoginAPITests.java      # Testes de API com RestAssured
│       │           ├── ui/
│       │           │   └── LoginUITests.java       # Testes de UI com Selenium (Parameterized)
│       │           ├── base/
│       │           │   └── TestBase.java           # Classe base para testes
│       │           └── helpers/                    # Helpers de teste
│       └── resources/
│           ├── test-data/                          # Dados de teste
│           └── features/                           # Cucumber BDD (opcional)
├── pom.xml                                          # Configuração Maven e Dependências
├── Dockerfile                                       # Arquivo Docker para containerização
├── docker-compose.yml                              # Orquestração de containers
├── CONTRIBUTING.md                                  # Guia de contribuição
├── cenarios-de-teste.md                            # Documentação de cenários (14 cenários)
├── README.md                                        # Este arquivo
└── .gitignore                                       # Arquivos ignorados pelo Git
```

## Tecnologias Utilizadas

- **Java 17 (LTS)**
- **Maven** - Gerenciamento de dependências
- **Selenium WebDriver 4.15.0** - Automação de interface web
- **RestAssured 5.3.2** - Testes de API
- **JUnit 5.10.0** - Framework de testes
- **Allure Framework 2.24.0** - Geração de relatórios de testes
- **Docker & Docker Compose** - Containerização e orquestração
- **GitHub Actions** - CI/CD Pipeline

## Pré-requisitos

### Execução Local
- Java JDK 17 ou superior
- Maven 3.6 ou superior
- Navegador Chrome instalado

### Execução via Docker (Recomendado)
- Docker
- Docker Compose

## Configuração

1. Clone o repositório
2. Configure as propriedades no arquivo `src/main/resources/config.properties`:

```properties
base.url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
valid.username=Admin
valid.password=admin123
browser=chrome
headless=false
```

## Executando os Testes

### Via Docker (Recomendado)

O comando abaixo sobe o ambiente completo (testes + Selenium Grid) e executa a suite de testes:

```bash
docker-compose up --build --abort-on-container-exit
```

### Via Maven (Local)

#### Executar todos os testes
```bash
mvn test
```

#### Executar apenas testes de UI
```bash
mvn test -Dtest=com.orangehrm.ui.LoginUITests
```

#### Executar apenas testes de API
```bash
mvn test -Dtest=LoginAPITests
```

## Padrões e Boas Práticas Utilizadas

- **Data Driven Testing (DDT)** - Utilização de `@ParameterizedTest` para cenários de login.
- **Page Object Pattern (POM)** - Separação estrutural de lógica e elementos.
- **Containerização** - Testes isolados e reproduzíveis via Docker.
- **CI/CD** - Pipeline com cache e execução agendada.

## CI/CD Pipeline

O projeto utiliza **GitHub Actions** para Integração Contínua.

### Funcionalidades do Pipeline
- **Execução Agendada:** Roda diariamente à meia-noite.
- **Cache Otimizado:** Cache inteligente para dependências Maven.
- **Matriz de Testes:** Execução paralela de API e UI (Login).
- **Relatórios Allure:** Publicação automática no GitHub Pages.

> **Nota:** O teste de Regressão Visual (`VisualRegressionTests`) está excluído do CI pois requer baseline manual. Execute localmente com `mvn test -Dtest=VisualRegressionTests`.

### Como Acessar os Relatórios
Ao final da execução do workflow no GitHub Actions, o artefato `allure-report-consolidado` estará disponível para download, e o relatório HTML será publicado no GitHub Pages (branch `gh-pages`).

## Últimas Atualizações (Changelog)

- **Atualização Java:** Migração do projeto para **Java 17 LTS**.
- **Refatoração de Testes:** Implementação de **DDT (Data Driven Testing)** nos testes de login UI, otimizando a cobertura de cenários negativos.
- **Containerização:** Criação de `Dockerfile` e `docker-compose.yml` para execução isolada dos testes.
- **CI/CD:** Melhorias no pipeline do GitHub Actions, incluindo execução agendada (Cron) e otimização de cache.
- **Visual Regression:** Adicionado teste de regressão visual com AShot para validação de layout.

## Demonstração Visual

![Execução dos Testes](docs/demo.gif)

*Nota: Para gerar este GIF, utilize uma ferramenta de gravação de tela durante a execução local e salve o arquivo em `docs/demo.gif`.*

## Regressão Visual Validação

O projeto implementa validação visual automatizada utilizando **AShot**.

### Como funciona:
1. O teste acessa a página de login.
2. Captura um screenshot do elemento Logo.
3. Compara com uma imagem de base (`src/test/resources/screenshots/baseline_logo.png`).
4. Se houver divergência, o teste falha e gera uma imagem de "diff".

### Executando Testes Visuais
```bash
mvn test -Dtest=VisualRegressionTests
```
*Na primeira execução, o teste falhará propositalmente e salvará a captura atual como sugestão para baseline.*



