package net.chabab.utilisateurservice;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UtilisateurServiceApplicationTests {

    @Test
    void contextLoads() {
        // Simple assertion that always passes if the context loads successfully
        assertTrue(true, "The application context should load without exceptions.");
    }
}

