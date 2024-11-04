package net.chabab.gestionanalyse.entites;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnalyseTest {
    @Test
    void testAnalyseBuilder() {
        Analyse analyse = Analyse.builder()
                .nom("Analyse de Sang")
                .description("Analyse pour vérifier les niveaux de glucose dans le sang.")
                .build();

//        assertEquals(1L, analyse.getId());
        assertEquals("Analyse de Sang", analyse.getNom());
        assertEquals("Analyse pour vérifier les niveaux de glucose dans le sang.", analyse.getDescription());
    }
}
