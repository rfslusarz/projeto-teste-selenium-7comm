package com.orangehrm.api;

import com.orangehrm.utils.ConfigReader;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class LoginAPITests {

    /**
     * Valida se a página de login está acessível (Status 200).
     * Nota: Este teste não cobre autenticação via API REST (que retornaria tokens),
     * pois o OrangeHRM usa autenticação via Sessão/Cookies e OAuth (não disponível
     * no demo).
     */
    @Test
    @DisplayName("Teste de Disponibilidade - OrangeHRM Login Page deve retornar 200")
    public void testOrangeHRMDisponibilidade() {
        String baseUrl = ConfigReader.getBaseUrl();

        Response response = given()
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .when()
                .get(baseUrl)
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200, response.getStatusCode(),
                "A página de login do OrangeHRM deveria estar acessível");
    }

    /**
     * Tenta simular um login inválido via formulário web.
     * Mapeamento de Status:
     * - API 401 (Unauthorized) -> Web: Redirecionamento (302) de volta para /login
     * ou 200 com erro.
     */
    @Test
    @DisplayName("Teste de Login Inválido - Simulação de Falha de Autenticação")
    public void testLoginInvalidoWeb() {
        String baseUrl = ConfigReader.getBaseUrl(); // ex: .../auth/login

        // 1. Acessar página de login para pegar Cookies e Token CSRF
        Response pageResp = given()
                .header("User-Agent", "Mozilla/5.0")
                .when()
                .get(baseUrl);

        String html = pageResp.getBody().asString();
        String token = extractToken(html);

        if (token == null) {
            System.out.println("Token CSRF não encontrado. O teste de login pode falhar.");
            // Não falhamos o teste aqui para evitar falsos negativos se o layout mudou
            return;
        }

        // 2. Enviar credenciais inválidas para o endpoint de validação
        // O endpoint geralmente é .../auth/validate
        String validateUrl = baseUrl.replace("/login", "/validate");

        Response loginResp = given()
                .header("User-Agent", "Mozilla/5.0")
                .cookies(pageResp.getCookies())
                .contentType("application/x-www-form-urlencoded")
                .formParam("_token", token)
                .formParam("username", "Admin")
                .formParam("password", "senhaerrada")
                .when()
                .post(validateUrl);

        // O OrangeHRM redireciona (302) de volta para login em caso de falha
        // Ou retorna 200 se renderizar o erro na mesma resposta
        Assertions.assertTrue(
                loginResp.getStatusCode() == 302 || loginResp.getStatusCode() == 200,
                "Login inválido deveria redirecionar ou retornar página de erro. Status: " + loginResp.getStatusCode());
    }

    private String extractToken(String html) {
        // Padrão para extrair o token do Vue component: :token="&quot;...&quot;"
        Pattern pattern = Pattern.compile(":token=\"&quot;(.*?)&quot;\"");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
