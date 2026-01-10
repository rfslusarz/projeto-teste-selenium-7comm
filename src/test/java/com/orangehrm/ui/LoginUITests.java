package com.orangehrm.ui;

import com.orangehrm.base.TestBase;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.ConfigReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class LoginUITests extends TestBase {

    @Test
    @DisplayName("Login válido com credenciais corretas - Validação de redirecionamento e tempo")
    public void testLoginValido() {
        LoginPage loginPage = new LoginPage(driver);
        
        long pageLoadTime = loginPage.measurePageLoadTime();
        Assertions.assertTrue(pageLoadTime <= 5000,
            "Tempo de carregamento da página deveria ser menor ou igual a 5 segundos. Tempo: " + pageLoadTime + "ms");
        
        loginPage.performLogin(
            ConfigReader.getValidUsername(),
            ConfigReader.getValidPassword()
        );

        long responseTime = loginPage.measureLoginResponseTime();
        Assertions.assertTrue(responseTime <= 5000,
            "Tempo de resposta do login deveria ser menor ou igual a 5 segundos. Tempo: " + responseTime + "ms");

        boolean redirected = loginPage.waitForRedirect("/dashboard", 5) || 
                           loginPage.waitForRedirect("/index.php/dashboard", 5) ||
                           loginPage.waitForRedirect("/web/index.php/dashboard", 5);

        String currentUrl = loginPage.getCurrentUrl();
        boolean loggedIn = redirected || !loginPage.isEmailInputDisplayed();

        Assertions.assertTrue(loggedIn, 
            "Login deveria ter sido realizado com sucesso e redirecionamento ocorrer. URL atual: " + currentUrl);
    }

    @Test
    @DisplayName("Login inválido - usuário e senha inválidos")
    public void testLoginInvalidoUsuarioESenhaInvalidos() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.performLogin("invaliduser", "senhaerrada");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String modalMessage = loginPage.getModalMessage();
        Assertions.assertTrue(
            loginPage.isModalDisplayed() || modalMessage.length() > 0,
            "Deveria exibir mensagem de erro para credenciais inválidas"
        );
    }

    @Test
    @DisplayName("Login inválido - usuário válido e senha inválida")
    public void testLoginInvalidoUsuarioValidoSenhaInvalida() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.performLogin(
            ConfigReader.getValidUsername(),
            "senhaerrada"
        );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String modalMessage = loginPage.getModalMessage();
        Assertions.assertTrue(
            loginPage.isModalDisplayed() || modalMessage.length() > 0,
            "Deveria exibir mensagem de erro para senha incorreta"
        );
    }

    @Test
    @DisplayName("Login inválido - usuário inválido e senha válida")
    public void testLoginInvalidoUsuarioInvalidoSenhaValida() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.performLogin("invaliduser", ConfigReader.getValidPassword());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String modalMessage = loginPage.getModalMessage();
        Assertions.assertTrue(
            loginPage.isModalDisplayed() || modalMessage.length() > 0,
            "Deveria exibir mensagem de erro para usuário inválido"
        );
    }

    @Test
    @DisplayName("Validação de tempo de carregamento da página de login")
    public void testTempoCarregamentoPaginaLogin() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Recarregar a página para garantir medição limpa
        driver.navigate().refresh();
        
        long loadTime = loginPage.measurePageLoadTime();
        
        Assertions.assertTrue(loadTime <= 5000, 
            "Tempo de carregamento da página de login deve ser <= 5 segundos. Tempo atual: " + loadTime + "ms");
    }

    @Test
    @DisplayName("Validação de redirecionamento após login")
    public void testRedirecionamentoAposLogin() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.performLogin(
            ConfigReader.getValidUsername(),
            ConfigReader.getValidPassword()
        );
        
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
