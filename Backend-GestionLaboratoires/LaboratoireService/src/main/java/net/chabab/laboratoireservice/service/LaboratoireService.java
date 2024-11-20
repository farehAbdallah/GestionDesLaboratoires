package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.repository.LaboratoireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratoireService {

    @Autowired
    private LaboratoireRepository laboratoireRepository;

    public List<Laboratoire> getAllLaboratoires() {
        return laboratoireRepository.findAll();
    }

    public Optional<Laboratoire> getLaboratoireById(Long id) {
        return laboratoireRepository.findById(id);
    }

    public Laboratoire saveLaboratoire(Laboratoire laboratoire) {
        return laboratoireRepository.save(laboratoire);
    }

    public Laboratoire updateLaboratoire(Long id, Laboratoire updatedLaboratoire) {
        return laboratoireRepository.findById(id).map(laboratoire -> {
            laboratoire.setNom(updatedLaboratoire.getNom());
            laboratoire.setLogo(updatedLaboratoire.getLogo());
            laboratoire.setNrc(updatedLaboratoire.getNrc());
            laboratoire.setActive(updatedLaboratoire.isActive());
            laboratoire.setDateActivation(updatedLaboratoire.getDateActivation());
            return laboratoireRepository.save(laboratoire);
        }).orElseThrow(() -> new RuntimeException("Laboratoire not found"));
    }

    public void deleteLaboratoire(Long id) {
        laboratoireRepository.deleteById(id);
    }
}
