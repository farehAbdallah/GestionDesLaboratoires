package net.chabab.laboratoireservice.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class LaboratoireTest {

    @Test
    void testLaboratoireBuilder() {
        // Given
        LocalDate activationDate = LocalDate.now();

        // When
        Laboratoire laboratoire = Laboratoire.builder()
                .nom("Micro Labo")
                .logo("C:\\labo1.png")
                .nrc("1234")
                .active(true)
                .dateActivation(activationDate)
                .build();

        // Then
        assertEquals("Micro Labo", laboratoire.getNom());
        assertEquals("C:\\labo1.png", laboratoire.getLogo());
        assertEquals("1234", laboratoire.getNrc());
        assertTrue(laboratoire.isActive());
        assertEquals(activationDate, laboratoire.getDateActivation());
    }
}
