package com.orangehrm.base;

import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class TestBase {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getBaseUrl());
    }

    @AfterEach
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
