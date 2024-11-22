package net.chabab.gestionanalyse.mapper;

import net.chabab.gestionanalyse.dtos.TestAnalyseDTO;
import net.chabab.gestionanalyse.entites.TestAnalyse;

public class TestAnalyseMapper {

    // Convertir de l'entité TestAnalyse en DTO TestAnalyse
    public static TestAnalyseDTO toTestAnalyseDTO(TestAnalyse testAnalyse) {
        return TestAnalyseDTO.builder()
                .id(testAnalyse.getId())
                .nomTest(testAnalyse.getNomTest())
                .valeurReference(testAnalyse.getValeurReference())
                .uniteDeReference(testAnalyse.getUniteDeReference())
                .intervalMinDeReference(testAnalyse.getIntervalMinDeReference())
                .intervalMaxDeReference(testAnalyse.getIntervalMaxDeReference())
                .details(testAnalyse.getDetails())
                .build();
    }

    // Convertir de DTO TestAnalyse en Entité TestAnalyse
    public static TestAnalyse toTestAnalyseEntity(TestAnalyseDTO testAnalyseDTO) {
        return TestAnalyse.builder()
                .id(testAnalyseDTO.getId())
                .nomTest(testAnalyseDTO.getNomTest())
                .valeurReference(testAnalyseDTO.getValeurReference())
                .uniteDeReference(testAnalyseDTO.getUniteDeReference())
                .intervalMinDeReference(testAnalyseDTO.getIntervalMinDeReference())
                .intervalMaxDeReference(testAnalyseDTO.getIntervalMaxDeReference())
                .details(testAnalyseDTO.getDetails())
                .build();
    }
}
