package net.chabab.patientservice.services;

import net.chabab.patientservice.dtos.ExaminDTO;

import java.util.List;

public interface ExaminService {
    ExaminDTO createExamin(ExaminDTO examinDTO);
    ExaminDTO getExaminById(Long id);
    List<ExaminDTO> getAllExamins();
    void deleteExamin(Long id);
}
