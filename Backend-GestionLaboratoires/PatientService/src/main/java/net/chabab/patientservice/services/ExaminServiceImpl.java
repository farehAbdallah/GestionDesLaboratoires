package net.chabab.patientservice.services.impl;

import net.chabab.patientservice.dtos.ExaminDTO;
import net.chabab.patientservice.entities.Dossier;
import net.chabab.patientservice.entities.Examin;
import net.chabab.patientservice.mappers.ExaminMapper;
import net.chabab.patientservice.repositories.DossierRepository;
import net.chabab.patientservice.repositories.ExaminRepository;
import net.chabab.patientservice.services.ExaminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExaminServiceImpl implements ExaminService {

    @Autowired
    private ExaminRepository examinRepository;

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String epreuveServiceUrl = "http://gestionanalyse-service/api/epreuves/";
    private final String testAnalyseServiceUrl = "http://gestionanalyse-service/api/test-analyses/";

    @Override
    public ExaminDTO createExamin(ExaminDTO examinDTO) {
        // Validate Dossier
        Dossier dossier = dossierRepository.findById(examinDTO.getFkNumDossier())
                .orElseThrow(() -> new RuntimeException("Dossier not found"));

        // Validate Epreuve
        if (!isEpreuveValid(examinDTO.getFkIdEpreuve())) {
            throw new RuntimeException("Invalid Epreuve ID: " + examinDTO.getFkIdEpreuve());
        }

        // Validate TestAnalyse
        if (!isTestAnalyseValid(examinDTO.getFkIdTestAnalyse())) {
            throw new RuntimeException("Invalid TestAnalyse ID: " + examinDTO.getFkIdTestAnalyse());
        }

        // Map and Save Examin
        Examin examin = ExaminMapper.INSTANCE.toEntity(examinDTO);
        examin.setDossier(dossier);
        Examin savedExamin = examinRepository.save(examin);

        return ExaminMapper.INSTANCE.toDto(savedExamin);
    }

    private boolean isEpreuveValid(Long epreuveId) {
        String url = epreuveServiceUrl + epreuveId;
        try {
            return restTemplate.getForObject(url, Boolean.class);
        } catch (Exception e) {
            throw new RuntimeException("Error validating Epreuve ID: " + epreuveId, e);
        }
    }

    private boolean isTestAnalyseValid(Long testAnalyseId) {
        String url = testAnalyseServiceUrl + testAnalyseId;
        try {
            return restTemplate.getForObject(url, Boolean.class);
        } catch (Exception e) {
            throw new RuntimeException("Error validating TestAnalyse ID: " + testAnalyseId, e);
        }
    }

    @Override
    public ExaminDTO getExaminById(Long id) {
        Examin examin = examinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Examin not found"));
        return ExaminMapper.INSTANCE.toDto(examin);
    }

    @Override
    public List<ExaminDTO> getAllExamins() {
        return examinRepository.findAll().stream()
                .map(ExaminMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteExamin(Long id) {
        if (!examinRepository.existsById(id)) {
            throw new RuntimeException("Examin not found");
        }
        examinRepository.deleteById(id);
    }
}
