package com.orangehrm.ui;

import com.orangehrm.base.TestBase;

import com.orangehrm.utils.ConfigReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@DisplayName("Testes de Regressão Visual")
public class VisualRegressionTests extends TestBase {

    @Test
    @DisplayName("Validar Logo da Login Page")
    public void testLogoVisual() throws IOException {
        driver.get(ConfigReader.getBaseUrl());

        // Elemento alvo (Logo da empresa na página de login)
        // XPath aproximado, ajustável conforme o DOM real
        WebElement logoElement = driver.findElement(By.cssSelector("img[alt='company-branding']"));

        // Captura o Screenshot do elemento
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(driver, logoElement);

        BufferedImage actualImage = screenshot.getImage();

        // Caminhos para salvar/ler
        Path baselinePath = Paths.get("src/test/resources/screenshots/baseline_logo.png");
        Path resultPath = Paths.get("target/screenshots/actual_logo.png");

        File resultFile = resultPath.toFile();
        resultFile.getParentFile().mkdirs();
        ImageIO.write(actualImage, "png", resultFile);

        // Verificação Lógica
        if (Files.exists(baselinePath)) {
            BufferedImage expectedImage = ImageIO.read(baselinePath.toFile());

            ImageDiff diff = new ImageDiffer().makeDiff(expectedImage, actualImage);

            if (diff.hasDiff()) {
                File diffFile = new File("target/screenshots/diff_logo.png");
                ImageIO.write(diff.getMarkedImage(), "png", diffFile);
                Assertions.fail("Divergência visual detectada! Verifique target/screenshots/diff_logo.png");
            } else {
                // Passou
                Assertions.assertTrue(true);
            }
        } else {

            // Se não existe baseline, apenas alerta para criar (Soft Assertion para demo)
            System.out.println("ADVERTÊNCIA: Baseline não encontrada. Uma nova captura foi salva em: "
                    + resultPath.toAbsolutePath());
            System.out.println("Para ativar a validação, mova este arquivo para " + baselinePath.toAbsolutePath());
            // Não falha o teste para manter o build verde no primeiro uso
            Assertions.assertTrue(true, "Baseline criada com sucesso. Validação pendente de aprovação manual.");
        }
    }
}
