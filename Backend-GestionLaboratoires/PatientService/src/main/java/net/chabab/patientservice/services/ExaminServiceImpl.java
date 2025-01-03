package net.chabab.patientservice.services;

import net.chabab.patientservice.dtos.EpreuveDTO;
import net.chabab.patientservice.dtos.ExaminDTO;
import net.chabab.patientservice.entities.Dossier;
import net.chabab.patientservice.entities.Examin;
import net.chabab.patientservice.feign.EpreuveFeignClient;
import net.chabab.patientservice.mappers.ExaminMapper;
import net.chabab.patientservice.repositories.DossierRepository;
import net.chabab.patientservice.repositories.ExaminRepository;
import net.chabab.patientservice.services.ExaminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExaminServiceImpl implements ExaminService {

    @Autowired
    private ExaminRepository examinRepository;

    @Autowired
    private DossierRepository dossierRepository;

    @Autowired
    private EpreuveFeignClient epreuveFeignClient;  // Inject Feign Client for Epreuve

    @Override
    public ExaminDTO createExamin(ExaminDTO examinDTO) {
        // Validate Dossier
        Dossier dossier = dossierRepository.findById(examinDTO.getFkNumDossier())
                .orElseThrow(() -> new RuntimeException("Dossier not found"));

        // Récupérer les informations de l'Epreuve via Feign
        EpreuveDTO epreuveDTO = epreuveFeignClient.getEpreuveById(examinDTO.getFkIdEpreuve());

//        // Ajouter l'Epreuve dans le DTO Examin
//        examinDTO.setEpreuve(epreuveDTO);

        // Mapper ExaminDTO vers Examin et enregistrer en base
        Examin examin = ExaminMapper.INSTANCE.toEntity(examinDTO);
        examin.setDossier(dossier);  // Lier le Dossier

        Examin savedExamin = examinRepository.save(examin);
        return ExaminMapper.INSTANCE.toDto(savedExamin);
    }

    @Override
    public ExaminDTO getExaminById(Long id) {
        Examin examin = examinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Examin not found"));
        return ExaminMapper.INSTANCE.toDto(examin);
    }

    @Override
    public List<ExaminDTO> getAllExamins() {
        return examinRepository.findAll()
                .stream()
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
