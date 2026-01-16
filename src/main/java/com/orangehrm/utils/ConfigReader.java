package com.orangehrm.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/resources/config.properties"
            );
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar arquivo de configuração", e);
        }
    }

    public static String getProperty(String key) {
        // Prioriza propriedades do sistema (útil para CI/CD)
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.isEmpty()) {
            return systemProperty;
        }
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getValidUsername() {
        return getProperty("valid.username");
    }

    public static String getValidPassword() {
        return getProperty("valid.password");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }

    public static String getApiBaseUrl() {
        return getProperty("api.base.url");
    }

    public static int getApiTimeout() {
        return Integer.parseInt(getProperty("api.timeout"));
    }

    public static int getMaxResponseTime() {
        return Integer.parseInt(getProperty("api.max.response.time"));
    }
}
