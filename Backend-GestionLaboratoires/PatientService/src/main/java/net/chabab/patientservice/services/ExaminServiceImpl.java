package net.chabab.patientservice.services;

import net.chabab.patientservice.dtos.ExaminDTO;
import net.chabab.patientservice.entities.Dossier;
import net.chabab.patientservice.entities.Examin;
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

    @Override
    public ExaminDTO createExamin(ExaminDTO examinDTO) {
        // Validate Dossier
        Dossier dossier = dossierRepository.findById(examinDTO.getFkNumDossier())
                .orElseThrow(() -> new RuntimeException("Dossier not found"));

        // Map and Save Examin
        Examin examin = ExaminMapper.INSTANCE.toEntity(examinDTO);
        examin.setDossier(dossier);
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
