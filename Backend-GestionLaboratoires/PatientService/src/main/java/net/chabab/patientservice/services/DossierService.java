package net.chabab.patientservice.services;

import net.chabab.patientservice.dtos.DossierDTO;

import java.util.List;

public interface DossierService {
    DossierDTO createDossier(DossierDTO dossierDTO);
    DossierDTO getDossierById(Long id);
    List<DossierDTO> getAllDossiers();
    DossierDTO updateDossier(Long id, DossierDTO dossierDTO);
    void deleteDossier(Long id);
}
