package com.orangehrm.ui;

import com.orangehrm.base.TestBase;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.ConfigReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LoginUITests extends TestBase {

        @Test
        @DisplayName("Login válido com credenciais corretas - Validação de redirecionamento e tempo")
        public void testLoginValido() {
                LoginPage loginPage = new LoginPage(driver);

                long pageLoadTime = loginPage.measurePageLoadTime();
                Assertions.assertTrue(pageLoadTime <= 10000,
                                "Tempo de carregamento da página deveria ser menor ou igual a 10 segundos. Tempo: "
                                                + pageLoadTime
                                                + "ms");

                loginPage.performLogin(
                                ConfigReader.getValidUsername(),
                                ConfigReader.getValidPassword());

                long responseTime = loginPage.measureLoginResponseTime();
                Assertions.assertTrue(responseTime <= 10000,
                                "Tempo de resposta do login deveria ser menor ou igual a 10 segundos. Tempo: "
                                                + responseTime + "ms");

                boolean redirected = loginPage.waitForRedirect("/dashboard", 5) ||
                                loginPage.waitForRedirect("/index.php/dashboard", 5) ||
                                loginPage.waitForRedirect("/web/index.php/dashboard", 5);

                String currentUrl = loginPage.getCurrentUrl();
                boolean loggedIn = redirected || !loginPage.isEmailInputDisplayed();

                Assertions.assertTrue(loggedIn,
                                "Login deveria ter sido realizado com sucesso e redirecionamento ocorrer. URL atual: "
                                                + currentUrl);
        }

        @ParameterizedTest(name = "Login inválido: {0}")
        @CsvSource({
                        "Usuário e Senha inválidos, invaliduser, senhaerrada",
                        "Usuário válido e Senha inválida, Admin, senhaerrada",
                        "Usuário inválido e Senha válida, invaliduser, admin123"
        })
        @DisplayName("Teste de Login Negativo (DDT)")
        public void testLoginInvalido(String cenario, String username, String password) {
                LoginPage loginPage = new LoginPage(driver);

                // Ajuste para pegar do config se for o valor placeholder 'Admin' ou 'admin123'
                // Isso permite manter o teste dinâmico se as credenciais mudarem no config
                String user = username.equals("Admin") ? ConfigReader.getValidUsername() : username;
                String pass = password.equals("admin123") ? ConfigReader.getValidPassword() : password;

                loginPage.performLogin(user, pass);

                try {
                        Thread.sleep(2000);
                } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                }

                String modalMessage = loginPage.getModalMessage();
                Assertions.assertTrue(
                                loginPage.isModalDisplayed() || modalMessage.length() > 0,
                                "Falha no cenário: " + cenario + ". Deveria exibir mensagem de erro.");
        }

        @Test
        @DisplayName("Validação de tempo de carregamento da página de login")
        public void testTempoCarregamentoPaginaLogin() {
                LoginPage loginPage = new LoginPage(driver);

                // Recarregar a página para garantir medição limpa
                driver.navigate().refresh();

                long loadTime = loginPage.measurePageLoadTime();

                Assertions.assertTrue(loadTime <= 10000,
                                "Tempo de carregamento da página de login deve ser <= 10 segundos. Tempo atual: "
                                                + loadTime + "ms");
        }

        @Test
        @DisplayName("Validação de redirecionamento após login")
        public void testRedirecionamentoAposLogin() {
                LoginPage loginPage = new LoginPage(driver);

                loginPage.performLogin(
                                ConfigReader.getValidUsername(),
                                ConfigReader.getValidPassword());

                boolean redirected = loginPage.waitForRedirect("/dashboard", 5) ||
                                loginPage.waitForRedirect("/index.php/dashboard", 5) ||
                                loginPage.waitForRedirect("/web/index.php/dashboard", 5);

                Assertions.assertTrue(redirected,
                                "Usuário deve ser redirecionado para o dashboard após login com sucesso");

                String currentUrl = loginPage.getCurrentUrl();
                Assertions.assertTrue(currentUrl.contains("dashboard"),
                                "URL deve conter 'dashboard'. URL atual: " + currentUrl);
        }
}
