package net.chabab.gestionanalyse.service;

import net.chabab.gestionanalyse.dtos.TestAnalyseDTO;
import net.chabab.gestionanalyse.entites.TestAnalyse;
import net.chabab.gestionanalyse.repository.TestAnalyseRepository;
import net.chabab.gestionanalyse.mapper.TestAnalyseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestAnalyseService {

    @Autowired
    private TestAnalyseRepository testAnalyseRepository;

    // Créer un nouveau TestAnalyse
    public TestAnalyseDTO createTestAnalyse(TestAnalyseDTO testAnalyseDTO) {
        TestAnalyse testAnalyse = TestAnalyseMapper.toTestAnalyseEntity(testAnalyseDTO);
        TestAnalyse savedTestAnalyse = testAnalyseRepository.save(testAnalyse);
        return TestAnalyseMapper.toTestAnalyseDTO(savedTestAnalyse);
    }

    // Obtenir un TestAnalyse par son ID
    public TestAnalyseDTO getTestAnalyseById(Long id) {
        TestAnalyse testAnalyse = testAnalyseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestAnalyse not found"));
        return TestAnalyseMapper.toTestAnalyseDTO(testAnalyse);
    }

    // Obtenir tous les TestAnalyses
    public List<TestAnalyseDTO> getAllTestAnalyses() {
        List<TestAnalyse> testAnalyses = testAnalyseRepository.findAll();
        return testAnalyses.stream()
                .map(TestAnalyseMapper::toTestAnalyseDTO)
                .collect(Collectors.toList());
    }

    // Mettre à jour un TestAnalyse
    public TestAnalyseDTO updateTestAnalyse(Long id, TestAnalyseDTO testAnalyseDTO) {
        TestAnalyse existingTestAnalyse = testAnalyseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestAnalyse not found"));

        existingTestAnalyse.setNomTest(testAnalyseDTO.getNomTest());
        existingTestAnalyse.setValeurReference(testAnalyseDTO.getValeurReference());
        existingTestAnalyse.setUniteDeReference(testAnalyseDTO.getUniteDeReference());
        existingTestAnalyse.setIntervalMinDeReference(testAnalyseDTO.getIntervalMinDeReference());
        existingTestAnalyse.setIntervalMaxDeReference(testAnalyseDTO.getIntervalMaxDeReference());
        existingTestAnalyse.setDetails(testAnalyseDTO.getDetails());

        TestAnalyse updatedTestAnalyse = testAnalyseRepository.save(existingTestAnalyse);
        return TestAnalyseMapper.toTestAnalyseDTO(updatedTestAnalyse);
    }

    // Supprimer un TestAnalyse
    public void deleteTestAnalyse(Long id) {
        testAnalyseRepository.deleteById(id);
    }
}
