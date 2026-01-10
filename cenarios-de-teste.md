# Cenários de Teste - OrangeHRM

**Aplicação:** OrangeHRM (https://opensource-demo.orangehrmlive.com/)  
**Credenciais padrão:** Admin / admin123  
**Status dos testes:** 9 testes de UI automatizados e executados com sucesso

## Parte A: Análise de Cenários de Teste

### Classificação dos Tipos de Teste

- **FT (Funcional - Teste Positivo):** Verifica se a funcionalidade funciona conforme esperado
- **FN (Funcional - Teste Negativo):** Verifica comportamento com dados inválidos ou condições de erro
- **SEG (Segurança):** Verifica aspectos de segurança e proteção contra vulnerabilidades
- **PERF (Performance):** Verifica tempos de resposta e carregamento

---

## Cenários Detalhados

### 1. Login Válido - Perfil Admin
**Tipo:** FT - Funcional Positivo  
**Prioridade:** Alta  
**Camada de Automação:** UI Implementado  
**Perfil:** Administrador  
**Pré-condição:** Usuário administrador disponível no sistema demo  
**Dados de teste:**
- Username: Admin
- Senha: admin123

**Passos:**
1. Acessar a página de login do OrangeHRM (https://opensource-demo.orangehrmlive.com/web/index.php/auth/login)
2. Verificar tempo de carregamento da página (máximo 5 segundos)
3. Preencher o campo username com credenciais válidas
4. Preencher o campo senha com senha válida
5. Clicar no botão "Login"
6. Verificar tempo de resposta do login (máximo 5 segundos)

**Resultado esperado:**
- Login realizado com sucesso 
- Redirecionamento para página dashboard dentro de 5 segundos 
- URL muda para `/web/index.php/dashboard` 

**Validações realizadas:**
- Tempo de carregamento da página ≤ 5 segundos
- Tempo de resposta do login ≤ 5 segundos
- Redirecionamento para dashboard validado
- URL atualizada corretamente

---

### 2. Login Válido - Perfil Usuário Comum
**Tipo:** FT - Funcional Positivo  
**Prioridade:** Média  
**Camada de Automação:** UI (Não implementado - usando apenas perfil Admin no demo)  
**Perfil:** Usuário comum  
**Nota:** A versão demo do OrangeHRM utiliza apenas o perfil Admin. Este cenário seria aplicável em ambiente de produção.

---

### 3. Login Inválido - Usuário e Senha Inválidos
**Tipo:** FN - Funcional Negativo  
**Prioridade:** Alta  
**Camada de Automação:** UI Implementado  
**Dados de teste:**
- Username: invaliduser
- Senha: senhaerrada

**Passos:**
1. Acessar a página de login do OrangeHRM
2. Preencher o campo username com usuário inválido
3. Preencher o campo senha com senha inválida
4. Clicar no botão "Login"
5. Verificar tempo de resposta (máximo 5 segundos)

**Resultado esperado:**
-  Exibição de mensagem de erro de credenciais inválidas
-  Mensagem de erro aparece dentro de 5 segundos
-  Não ocorre redirecionamento
-  Campos permanecem acessíveis para nova tentativa

**Validações realizadas:**
-  Mensagem de erro exibida
-  Modal/alert visível
-  URL permanece na página de login

---

### 4. Login Inválido - Usuário Válido e Senha Inválida
**Tipo:** FN - Funcional Negativo  
**Prioridade:** Alta  
**Camada de Automação:** UI Implementado  
**Dados de teste:**
- Username: Admin
- Senha: senhaerrada

**Passos:**
1. Acessar a página de login do OrangeHRM
2. Preencher o campo username com usuário válido (Admin)
3. Preencher o campo senha com senha incorreta
4. Clicar no botão "Login"
5. Verificar tempo de resposta (máximo 5 segundos)

**Resultado esperado:**
-  Exibição de mensagem de erro indicando credenciais inválidas
-  Não ocorre redirecionamento
-  Usuário não é bloqueado após uma tentativa

---

### 5. Login Inválido - Usuário Inválido e Senha Válida
**Tipo:** FN - Funcional Negativo  
**Prioridade:** Média  
**Camada de Automação:** UI Implementado  
**Dados de teste:**
- Username: invaliduser
- Senha: admin123

**Passos:**
1. Acessar a página de login do OrangeHRM
2. Preencher o campo username com usuário inexistente
3. Preencher o campo senha com senha válida
4. Clicar no botão "Login"
5. Verificar tempo de resposta (máximo 5 segundos)

**Resultado esperado:**
-  Exibição de mensagem de erro genérica
-  Segurança: não revela informação sobre existência de usuário

---

### 6. Login em Branco - Usuário em Branco e Senha Válida
**Tipo:** FN - Funcional Negativo  
**Prioridade:** Média  
**Camada de Automação:** UI Implementado  
**Dados de teste:**
- Username: (vazio)
- Senha: admin123

**Passos:**
1. Acessar a página de login do OrangeHRM
2. Deixar o campo username em branco
3. Preencher o campo senha com senha válida
4. Clicar no botão "Login"
5. Verificar validação

**Resultado esperado:**
-  Validação de campo obrigatório
-  Página permanece na tela de login
-  Campos permanecem visíveis

---

### 7. Login em Branco - Usuário Válido e Senha em Branco
**Tipo:** FN - Funcional Negativo  
**Prioridade:** Média  
**Camada de Automação:** UI Implementado  
**Dados de teste:**
- Username: Admin
- Senha: (vazio)

**Passos:**
1. Acessar a página de login do OrangeHRM
2. Preencher o campo username com usuário válido (Admin)
3. Deixar o campo senha em branco
4. Clicar no botão "Login"
5. Verificar validação

**Resultado esperado:**
-  Validação de campo obrigatório
-  Página permanece na tela de login
-  Campos permanecem visíveis

---

### 8. Login em Branco - Usuário e Senha em Branco
**Tipo:** FN - Funcional Negativo  
**Prioridade:** Média  
**Camada de Automação:** UI Implementado  
**Dados de teste:**
- Username: (vazio)
- Senha: (vazio)

**Passos:**
1. Acessar a página de login do OrangeHRM
2. Deixar ambos os campos em branco
3. Clicar no botão "Login"
4. Verificar validação

**Resultado esperado:**
-  Validação de ambos os campos
-  Página permanece na tela de login
-  Ambos os campos permanecem visíveis

---

### 9. Login com Username em Formato Inválido
**Tipo:** FN - Funcional Negativo  
**Prioridade:** Baixa  
**Camada de Automação:** UI (Não implementado)  
**Nota:** O OrangeHRM utiliza username ao invés de email, e não há validação específica de formato para username além de campo obrigatório.

---

### 10. Tentativas Múltiplas de Login Inválido - Bloqueio de Conta
**Tipo:** SEG - Segurança  
**Prioridade:** Alta  
**Camada de Automação:** Manual  
**Nota:** Não implementado na versão demo do OrangeHRM. Seria necessário ambiente de produção/teste específico para validar bloqueio por tentativas.

---

### 11. Redirecionamento Após Login Válido
**Tipo:** FT - Funcional Positivo  
**Prioridade:** Alta  
**Camada de Automação:** UI Implementado  
**Perfil:** Admin

**Passos:**
1. Realizar login válido (Admin/admin123)
2. Verificar URL de redirecionamento
3. Verificar tempo de redirecionamento (máximo 5 segundos)
4. Verificar elementos da página destino

**Resultado esperado:**
-  Redirecionamento para `/web/index.php/dashboard`
-  Tempo de redirecionamento dentro de 5 segundos
-  URL atualizada corretamente
-  Campos de login não estão mais visíveis (validação indireta de sucesso)

---

### 12. Teste de Segurança - SQL Injection
**Tipo:** SEG - Segurança  
**Prioridade:** Alta  
**Camada de Automação:** API (Manual opcional)  
**Dados de teste:**
- Email: admin' OR '1'='1
- Senha: qualquer

**Passos:**
1. Tentar login com SQL injection no campo email
2. Verificar resposta do servidor

**Resultado esperado:**
- Status HTTP 400 (Bad Request)
- Erro tratado adequadamente
- Nenhuma informação sensível exposta
- Log de tentativa de ataque registrado

---

### 13. Teste de Segurança - XSS no Campo Email
**Tipo:** SEG - Segurança  
**Prioridade:** Alta  
**Camada de Automação:** UI (Manual)  
**Dados de teste:**
- Email: <script>alert('XSS')</script>
- Senha: admin123

**Passos:**
1. Inserir script XSS no campo email
2. Tentar realizar login
3. Verificar se script é executado

**Resultado esperado:**
- Script não executado
- Dados sanitizados
- Validação rejeita entrada maliciosa
- Mensagem de erro apropriada

---

### 14. Performance - Tempo de Carregamento da Página de Login
**Tipo:** PERF - Performance  
**Prioridade:** Média  
**Camada de Automação:** UI Implementado  
**Métrica:** Tempo de carregamento completo da página

**Passos:**
1. Acessar a página de login do OrangeHRM
2. Medir tempo até elementos principais estarem disponíveis
3. Validar tempo de carregamento

**Resultado esperado:**
-  Tempo de carregamento completo: máximo 5 segundos
-  Todos os elementos interativos (username, password, botão login) disponíveis dentro de 5 segundos
-  Validação de visibilidade dos campos (username, password, botão login)                   

---

## Matriz de Automação

| ID | Cenário | Tipo | Prioridade | UI Automatizado | API Automatizado | Manual |
|----|---------|------|------------|-----------------|------------------|--------|
| 1 | Login Válido - Perfil Admin | FT | Alta | SIM | SIM | NAO |
| 2 | Login Válido - Perfil Usuário | FT | Alta | SIM | SIM | NAO |
| 3 | Login Inválido - Ambos Inválidos | FN | Alta | SIM | SIM | NAO |
| 4 | Login Inválido - Senha Incorreta | FN | Alta | SIM | SIM | NAO |
| 5 | Login Inválido - Usuário Inexistente | FN | Média | SIM | SIM | NAO |
| 6 | Login em Branco - Email Vazio | FN | Média | SIM | NAO | NAO |
| 7 | Login em Branco - Senha Vazia | FN | Média | SIM | NAO | NAO |
| 8 | Login em Branco - Ambos Vazios | FN | Média | SIM | NAO | NAO |
| 9 | Email Formato Inválido | FN | Média | SIM | NAO | NAO |
| 10 | Bloqueio por Tentativas | SEG | Alta | NAO | SIM | NAO |
| 11 | Redirecionamento | FT | Alta | SIM | NAO | NAO |
| 12 | SQL Injection | SEG | Alta | NAO | SIM | SIM |
| 13 | XSS | SEG | Alta | NAO | NAO | SIM |
| 14 | Performance - Carregamento | PERF | Média | NAO | NAO | SIM |

**Legenda:**
- SIM = Implementado/Automatizado
- NAO = Não automatizado / Manual

---

## Resumo Estatístico

**Total de Cenários:** 14

**Por Tipo:**
- **Funcional Positivo (FT):** 3 cenários
- **Funcional Negativo (FN):** 7 cenários
- **Segurança (SEG):** 3 cenários
- **Performance (PERF):** 1 cenário

**Por Prioridade:**
- **Alta:** 10 cenários
- **Média:** 4 cenários

**Por Camada (Status Atual):**
- **UI Automatizado (OrangeHRM):** 9 cenários Implementados e executados
- **API Automatizado (ReqRes - exemplo):** 8 cenários (API externa de exemplo)
- **Manual:** 3 cenários (SQL Injection, XSS, Bloqueio múltiplas tentativas)

**Considerações:**
- Redirecionamentos validados
- Mensagens de erro documentadas
- Tempos de carregamento especificados (máximo 5 segundos)
- Perfis de usuário considerados (Admin e Usuário Comum)
- Aspectos de segurança contemplados

---

## Estrutura Proposta para Automação

### Camada UI (Selenium) - Implementado
- **Page Objects:** LoginPage
- **Utilitários:** DriverManager, ConfigReader
- **Testes:** LoginUITests (9 cenários implementados e executados com sucesso)
- **Frameworks:** Selenium WebDriver 4.15.0, JUnit 5.10.0, WebDriverManager 5.6.2
- **Aplicação:** OrangeHRM (https://opensource-demo.orangehrmlive.com/)
- **Credenciais:** Admin / admin123

### Camada API (RestAssured) - ReqRes (Exemplo)
- **API Externa:** ReqRes (https://reqres.in) - API pública para exemplos
- **Endpoints:** /api/login, /api/register
- **Validações:** Status codes (200, 400), Token
- **Testes:** LoginAPITests (8 cenários - API externa de exemplo)
- **Frameworks:** RestAssured 5.3.2, JUnit 5
- **Nota:** Estes testes não estão relacionados ao OrangeHRM, são exemplos de testes de API REST

### Camada Manual
- **Ferramentas:** Browser DevTools, Postman, OWASP ZAP
- **Testes:** XSS, Performance detalhada, Penetration testing avançado

### Estrutura de Pastas Proposta:
```
src/
├── main/
│   ├── java/com/bugbank/
│   │   ├── pages/          # Page Objects
│   │   ├── utils/          # Utilitários
│   │   └── models/         # Modelos de dados
│   └── resources/
│       └── config.properties
├── test/
│   ├── java/com/bugbank/
│   │   ├── ui/             # Testes de UI
│   │   ├── api/            # Testes de API
│   │   ├── base/           # Classes base
│   │   └── helpers/        # Helpers de teste
│   └── resources/
│       ├── test-data/      # Dados de teste
│       └── features/       # Cucumber (opcional)
```
