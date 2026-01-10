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

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(name = "username")
    private WebElement emailInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit'] | //button[contains(text(), 'Login')]")
    private WebElement accessButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickAccessButton() {
        wait.until(ExpectedConditions.elementToBeClickable(accessButton));
        accessButton.click();
    }

    public void performLogin(String username, String password) {
        enterEmail(username);
        enterPassword(password);
        clickAccessButton();
    }

    public String getModalMessage() {
        try {
            WebElement alertElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[@role='alert'] | //div[contains(@class, 'alert')] | //div[contains(@class, 'error')] | //p[contains(@class, 'error')] | //span[contains(@class, 'error')]")
                )
            );
            if (alertElement.isDisplayed()) {
                return alertElement.getText();
            }
        } catch (Exception e) {
            try {
                WebElement invalidCredentials = driver.findElement(
                    By.xpath("//p[contains(text(), 'Invalid') or contains(text(), 'Invalid credentials') or contains(text(), 'error')]")
                );
                if (invalidCredentials.isDisplayed()) {
                    return invalidCredentials.getText();
                }
            } catch (Exception ex) {
                try {
                    WebElement alertDiv = driver.findElement(
                        By.xpath("//div[contains(@class, 'oxd-alert')] | //div[@role='alert']")
                    );
                    if (alertDiv.isDisplayed()) {
                        return alertDiv.getText();
                    }
                } catch (Exception exc) {
                }
            }
        }
        return "";
    }

    public boolean isModalDisplayed() {
        try {
            WebElement alertElement = driver.findElement(
                By.xpath("//div[@role='alert'] | //div[contains(@class, 'alert')] | //div[contains(@class, 'oxd-alert')]")
            );
            return alertElement.isDisplayed();
        } catch (Exception e) {
            try {
                WebElement errorElement = driver.findElement(
                    By.xpath("//p[contains(text(), 'Invalid') or contains(text(), 'error')] | //span[contains(@class, 'error')]")
                );
                return errorElement.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public long measurePageLoadTime() {
        long startTime = System.currentTimeMillis();
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public long measureLoginResponseTime() {
        long startTime = System.currentTimeMillis();
        // Wait for URL change or dashboard element
        try {
            wait.until(ExpectedConditions.urlContains("dashboard"));
        } catch (Exception e) {
            // Se falhar o redirecionamento, o teste vai falhar na asserção, mas precisamos retornar o tempo
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public boolean waitForRedirect(String partialUrl, int timeoutInSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return customWait.until(ExpectedConditions.urlContains(partialUrl));
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isEmailInputDisplayed() {
        try {
            return emailInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
