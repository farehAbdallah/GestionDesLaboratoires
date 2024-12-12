package net.chabab.gestionanalyse.service;

import net.chabab.gestionanalyse.dtos.TestAnalyseDTO;
import net.chabab.gestionanalyse.entities.Analyse;
import net.chabab.gestionanalyse.entities.TestAnalyse;
import net.chabab.gestionanalyse.mapper.TestAnalyseMapper;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import net.chabab.gestionanalyse.repository.TestAnalyseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestAnalyseServiceImpl implements TestAnalyseService {

    @Autowired
    private TestAnalyseRepository testAnalyseRepository;

    @Autowired
    private AnalyseRepository analyseRepository;

    @Override
    public TestAnalyseDTO createTestAnalyse(TestAnalyseDTO testAnalyseDTO) {
        Analyse analyse = analyseRepository.findById(testAnalyseDTO.getFkIdAnalyse())
                .orElseThrow(() -> new RuntimeException("Analyse not found"));

        TestAnalyse testAnalyse = TestAnalyseMapper.INSTANCE.toEntity(testAnalyseDTO);
        testAnalyse.setAnalyse(analyse);
        TestAnalyse savedTestAnalyse = testAnalyseRepository.save(testAnalyse);

        return TestAnalyseMapper.INSTANCE.toDto(savedTestAnalyse);
    }

    @Override
    public TestAnalyseDTO getTestAnalyseById(Long id) {
        TestAnalyse testAnalyse = testAnalyseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestAnalyse not found"));

        return TestAnalyseMapper.INSTANCE.toDto(testAnalyse);
    }

    @Override
    public List<TestAnalyseDTO> getAllTestAnalyses() {
        return testAnalyseRepository.findAll().stream()
                .map(TestAnalyseMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestAnalyseDTO> getTestAnalysesByAnalyseId(Long fkIdAnalyse) {
        return testAnalyseRepository.findByAnalyse_Id(fkIdAnalyse).stream()
                .map(TestAnalyseMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TestAnalyseDTO updateTestAnalyse(Long id, TestAnalyseDTO testAnalyseDTO) {
        TestAnalyse testAnalyse = testAnalyseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestAnalyse not found"));

        Analyse analyse = analyseRepository.findById(testAnalyseDTO.getFkIdAnalyse())
                .orElseThrow(() -> new RuntimeException("Analyse not found"));

        testAnalyse.setNomTest(testAnalyseDTO.getNomTest());
        testAnalyse.setSousEpreuve(testAnalyseDTO.getSousEpreuve());
        testAnalyse.setIntervalMinDeReference(testAnalyseDTO.getIntervalMinDeReference());
        testAnalyse.setIntervalMaxDeReference(testAnalyseDTO.getIntervalMaxDeReference());
        testAnalyse.setUniteDeReference(testAnalyseDTO.getUniteDeReference());
        testAnalyse.setDetails(testAnalyseDTO.getDetails());
        testAnalyse.setAnalyse(analyse);

        TestAnalyse updatedTestAnalyse = testAnalyseRepository.save(testAnalyse);
        return TestAnalyseMapper.INSTANCE.toDto(updatedTestAnalyse);
    }

    @Override
    public void deleteTestAnalyse(Long id) {
        if (!testAnalyseRepository.existsById(id)) {
            throw new RuntimeException("TestAnalyse not found");
        }
        testAnalyseRepository.deleteById(id);
    }
}
