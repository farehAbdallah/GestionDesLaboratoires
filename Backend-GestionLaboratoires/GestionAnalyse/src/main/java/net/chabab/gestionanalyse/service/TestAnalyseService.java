package net.chabab.gestionanalyse.service;

import net.chabab.gestionanalyse.dtos.TestAnalyseDTO;

import java.util.List;

public interface TestAnalyseService {
    TestAnalyseDTO createTestAnalyse(TestAnalyseDTO testAnalyseDTO);
    TestAnalyseDTO getTestAnalyseById(Long id);
    List<TestAnalyseDTO> getAllTestAnalyses();
    List<TestAnalyseDTO> getTestAnalysesByAnalyseId(Long fkIdAnalyse);
    TestAnalyseDTO updateTestAnalyse(Long id, TestAnalyseDTO testAnalyseDTO);
    void deleteTestAnalyse(Long id);
}
