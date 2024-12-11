package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.LaboratoireDTO;

import java.util.List;

public interface LaboratoireService {
    LaboratoireDTO createLaboratoire(LaboratoireDTO laboratoireDTO);
    LaboratoireDTO getLaboratoireById(Long id);
    List<LaboratoireDTO> getAllLaboratoires();
    LaboratoireDTO updateLaboratoire(Long id, LaboratoireDTO laboratoireDTO);
    boolean deleteLaboratoire(Long id);


}
