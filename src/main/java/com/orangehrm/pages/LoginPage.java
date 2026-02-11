package com.orangehrm.pages;

import com.orangehrm.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Página de Login.
 * Implementa o padrão Page Object para a tela de login.
 */
public final class LoginPage {
    /**
     * Driver do Selenium.
     */
    private WebDriver driver;

    /**
     * Wait explícito.
     */
    private WebDriverWait wait;

    /**
     * Campo de entrada de email/usuário.
     */
    @FindBy(name = "username")
    private WebElement emailInput;

    /**
     * Campo de entrada de senha.
     */
    @FindBy(name = "password")
    private WebElement passwordInput;

    /**
     * Botão de acesso (Login).
     */
    @FindBy(xpath = "//button[@type='submit'] | //button[contains(text(), 'Login')]")
    private WebElement accessButton;

    /**
     * Construtor da página.
     *
     * @param webDriver Instância do WebDriver
     */
    public LoginPage(final WebDriver webDriver) {
        this.driver = webDriver;
        this.wait = new WebDriverWait(webDriver,
                Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Preenche o campo de email/usuário.
     *
     * @param email Email ou usuário a ser preenchido
     */
    public void enterEmail(final String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    /**
     * Preenche o campo de senha.
     *
     * @param password Senha a ser preenchida
     */
    public void enterPassword(final String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    /**
     * Clica no botão de acesso.
     */
    public void clickAccessButton() {
        wait.until(ExpectedConditions.elementToBeClickable(accessButton));
        accessButton.click();
    }

    /**
     * Realiza o login completo.
     *
     * @param username Usuário
     * @param password Senha
     */
    public void performLogin(final String username, final String password) {
        enterEmail(username);
        enterPassword(password);
        clickAccessButton();
    }

    /**
     * Obtém a mensagem de erro exibida no modal ou alerta.
     *
     * @return Mensagem de erro ou string vazia se não encontrar
     */
    public String getModalMessage() {
        try {
            WebElement alertElement = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@role='alert'] "
                                    + "| //div[contains(@class, 'alert')] "
                                    + "| //div[contains(@class, 'error')] "
                                    + "| //p[contains(@class, 'error')] "
                                    + "| //span[contains(@class, 'error')]")));
            if (alertElement.isDisplayed()) {
                return alertElement.getText();
            }
        } catch (Exception e) {
            try {
                WebElement invalidCredentials = driver.findElement(
                        By.xpath("//p[contains(text(), 'Invalid') "
                                + "or contains(text(), 'Invalid credentials') "
                                + "or contains(text(), 'error')]"));
                if (invalidCredentials.isDisplayed()) {
                    return invalidCredentials.getText();
                }
            } catch (Exception ex) {
                try {
                    WebElement alertDiv = driver.findElement(
                            By.xpath("//div[contains(@class, 'oxd-alert')] "
                                    + "| //div[@role='alert']"));
                    if (alertDiv.isDisplayed()) {
                        return alertDiv.getText();
                    }
                } catch (Exception exc) {
                    // Ignora e retorna vazio
                }
            }
        }
        return "";
    }

    /**
     * Verifica se o modal de erro está visível.
     *
     * @return true se visível, false caso contrário
     */
    public boolean isModalDisplayed() {
        try {
            WebElement alertElement = driver.findElement(
                    By.xpath("//div[@role='alert'] "
                            + "| //div[contains(@class, 'alert')] "
                            + "| //div[contains(@class, 'oxd-alert')]"));
            return alertElement.isDisplayed();
        } catch (Exception e) {
            try {
                WebElement errorElement = driver.findElement(
                        By.xpath("//p[contains(text(), 'Invalid') "
                                + "or contains(text(), 'error')] "
                                + "| //span[contains(@class, 'error')]"));
                return errorElement.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /**
     * Mede o tempo de carregamento da página de login.
     *
     * @return Tempo em milissegundos
     */
    public long measurePageLoadTime() {
        long startTime = System.currentTimeMillis();
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * Mede o tempo de resposta do login.
     *
     * @return Tempo em milissegundos
     */
    public long measureLoginResponseTime() {
        long startTime = System.currentTimeMillis();
        // Wait for URL change or dashboard element
        try {
            wait.until(ExpectedConditions.urlContains("dashboard"));
        } catch (Exception e) {
            // Se falhar o redirecionamento, o teste vai falhar na asserção,
            // mas precisamos retornar o tempo
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * Aguarda o redirecionamento para uma URL parcial.
     *
     * @param partialUrl       Parte da URL esperada
     * @param timeoutInSeconds Tempo máximo de espera em segundos
     * @return true se redirecionou, false caso contrário
     */
    public boolean waitForRedirect(final String partialUrl,
            final int timeoutInSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver,
                    Duration.ofSeconds(timeoutInSeconds));
            return customWait.until(ExpectedConditions.urlContains(partialUrl));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém a URL atual.
     *
     * @return URL atual
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Verifica se o campo de email está visível.
     *
     * @return true se visível, false caso contrário
     */
    public boolean isEmailInputDisplayed() {
        try {
            return emailInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
