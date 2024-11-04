package net.chabab.gestionanalyse.web;

import net.chabab.gestionanalyse.entites.Analyse;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("selenium")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnalyseRestControllerSeleniumTest {

    @Autowired
    private AnalyseRepository analyseRepository;

    private WebDriver driver;
    private Analyse analyse1;
    private Analyse analyse2;

    @BeforeAll
    void setup() {
        // Specify the browser you want to use (can be parameterized)
        String browser = System.getProperty("browser", "edge");

        // Initialize WebDriver based on the selected browser
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }

        // Seed the database with test data
        analyse1 = analyseRepository.save(Analyse.builder()
                .nom("Analyse de Sang")
                .description("Analyse pour vérifier les niveaux de glucose dans le sang.")
                .build());

        analyse2 = analyseRepository.save(Analyse.builder()
                .nom("Analyse Urinaire")
                .description("Analyse pour vérifier les niveaux de créatinine dans l'urine.")
                .build());

        System.out.println("ID of Analyse de Sang: " + analyse1.getId());
        System.out.println("ID of Analyse Urinaire: " + analyse2.getId());
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
     void testGetAllAnalyses() {
        driver.get("http://localhost:8086/analyses");

        // Verify that seeded analysis names appear on the page
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Analyse de Sang"), "Page should contain 'Analyse de Sang'");
        assertTrue(pageSource.contains("Analyse Urinaire"), "Page should contain 'Analyse Urinaire'");
    }

    @Test
    void testGetAnalyseById() {
        driver.get("http://localhost:8086/analyses/" + analyse1.getId());

        WebElement body = driver.findElement(By.tagName("body"));
        String bodyText = body.getText();
        assertTrue(bodyText.contains("Analyse de Sang"), "Expected 'Analyse de Sang' not found in response body");
    }
}
