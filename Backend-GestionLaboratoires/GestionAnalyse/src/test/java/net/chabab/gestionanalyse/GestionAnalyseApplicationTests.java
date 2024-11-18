package net.chabab.gestionanalyse;

import net.chabab.gestionanalyse.web.AnalyseController;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GestionAnalyseApplicationTests {

    @Autowired
    private AnalyseController analyseRestController;

    @Autowired
    private AnalyseRepository analyseRepository;

    @Test
    void contextLoads() {
        // Simple assertion that always passes if the context loads successfully
        assertTrue(true, "The application context should load without exceptions.");
    }

    @Test
    void testAnalyseRestControllerBeanLoaded() {
        // Check that the AnalyseRestController bean is loaded
        assertThat(analyseRestController).isNotNull();
    }

    @Test
    void testAnalyseRepositoryBeanLoaded() {
        // Check that the AnalyseRepository bean is loaded
        assertThat(analyseRepository).isNotNull();
    }
}
