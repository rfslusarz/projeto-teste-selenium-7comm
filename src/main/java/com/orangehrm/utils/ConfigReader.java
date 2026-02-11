package com.orangehrm.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Leitor de configurações.
 * Responsável por carregar as propriedades do sistema e do arquivo
 * config.properties.
 */
public final class ConfigReader {
    /**
     * Propriedades carregadas do arquivo de configuração.
     */
    private static Properties properties;

    /**
     * Bloco de inicialização estática para carregar o arquivo.
     */
    static {
        try {
            properties = new Properties();
            try (java.io.InputStream inputStream = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties")) {
                if (inputStream == null) {
                    throw new RuntimeException(
                            "Arquivo config.properties não encontrado no classpath");
                }
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao carregar arquivo de configuração", e);
        }
    }

    /**
     * Construtor privado para evitar instaciação.
     */
    private ConfigReader() {
        // Utility class
    }

    /**
     * Obtém o valor de uma propriedade pela chave.
     * Prioriza propriedades do sistema (System.getProperty).
     *
     * @param key Chave da propriedade
     * @return Valor da propriedade
     */
    public static String getProperty(final String key) {
        // Prioriza propriedades do sistema (útil para CI/CD)
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.isEmpty()) {
            return systemProperty;
        }
        return properties.getProperty(key);
    }

    /**
     * Obtém a URL base da aplicação.
     *
     * @return URL base
     */
    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    /**
     * Obtém o nome de usuário válido.
     *
     * @return Nome de usuário
     */
    public static String getValidUsername() {
        return getProperty("valid.username");
    }

    /**
     * Obtém a senha válida.
     *
     * @return Senha
     */
    public static String getValidPassword() {
        return getProperty("valid.password");
    }

    /**
     * Obtém o navegador configurado.
     *
     * @return Nome do navegador (chrome, firefox, edge)
     */
    public static String getBrowser() {
        return getProperty("browser");
    }

    /**
     * Verifica se o modo headless está ativado.
     *
     * @return true se headless, false caso contrário
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }

    /**
     * Obtém o tempo de espera implícita.
     *
     * @return Tempo em segundos
     */
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    /**
     * Obtém o tempo de espera explícita.
     *
     * @return Tempo em segundos
     */
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }

    /**
     * Obtém a URL base da API.
     *
     * @return URL da API
     */
    public static String getApiBaseUrl() {
        return getProperty("api.base.url");
    }

    /**
     * Obtém o timeout da API.
     *
     * @return Tempo em milissegundos (presivelmente) ou segundos
     */
    public static int getApiTimeout() {
        return Integer.parseInt(getProperty("api.timeout"));
    }

    /**
     * Obtém o tempo máximo de resposta da API esperado.
     *
     * @return Tempo em milissegundos
     */
    public static int getMaxResponseTime() {
        return Integer.parseInt(getProperty("api.max.response.time"));
    }
}
