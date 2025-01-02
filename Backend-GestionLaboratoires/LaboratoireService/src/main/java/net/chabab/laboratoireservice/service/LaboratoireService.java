package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.LaboratoireDTO;
import net.chabab.laboratoireservice.entities.Adresse;
import net.chabab.laboratoireservice.entities.ContactLaboratoire;
import net.chabab.laboratoireservice.entities.Laboratoire;

import java.util.List;

public interface LaboratoireService {
    LaboratoireDTO createLaboratoire(LaboratoireDTO laboratoireDTO);
    LaboratoireDTO getLaboratoireById(Long id);
    List<LaboratoireDTO> getAllLaboratoires();
    LaboratoireDTO updateLaboratoire(Long id, LaboratoireDTO laboratoireDTO);
    boolean deleteLaboratoire(Long id);
    void sendLaboratoireDetails(Laboratoire laboratoire, Adresse adresse, ContactLaboratoire contactLaboratoire);



}
