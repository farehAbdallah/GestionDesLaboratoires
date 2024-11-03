package net.chabab.laboratoireservice.web;

import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.repository.LaboratoireRepository;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("selenium")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LaboratoireRestControllerSeleniumTest {

    @Autowired
    private LaboratoireRepository laboratoireRepository;

    private WebDriver driver;
    private Laboratoire labo1;
    private Laboratoire labo2;

    @BeforeAll
    public void setup() {
        // Specify the browser you want to use (can be parameterized)
        String browser = System.getProperty("browser", "edge");

        // Initialize WebDriver based on the selected browser
        switch (browser.toLowerCase()) {
            case "firefox":
                // Manage the Firefox driver
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                // Manage the Edge driver
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "chrome":
            default:
                // Manage the Chrome driver
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }

        // Seed the database with test data
        labo1 = laboratoireRepository.save(Laboratoire.builder()
                .nom("Micro Labo")
                .logo("C:\\labo1.png")
                .nrc("1234")
                .active(true)
                .dateActivation(LocalDate.now())
                .build());

        labo2 = laboratoireRepository.save(Laboratoire.builder()
                .nom("Wild Labo")
                .logo("C:\\labo2.png")
                .nrc("12345")
                .active(false)
                .dateActivation(LocalDate.now())
                .build());

        System.out.println("ID of Micro Labo: " + labo1.getId());
        System.out.println("ID of Wild Labo: " + labo2.getId());
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testGetAllLaboratoires() {
        driver.get("http://localhost:8085/laboratoires");

        // Verify that seeded laboratory names appear on the page
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Micro Labo"));
        assertTrue(pageSource.contains("Wild Labo"));
    }

    @Test
    public void testGetLaboratoireById() {
        driver.get("http://localhost:8085/laboratoires/" + labo1.getId());

        WebElement body = driver.findElement(By.tagName("body"));
        String bodyText = body.getText();
        assertTrue(bodyText.contains("Micro Labo"));  // Check for expected lab name
    }
}
