package net.chabab.patientservice.controllers;

import net.chabab.patientservice.dtos.DossierDTO;
import net.chabab.patientservice.services.DossierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dossiers")
@CrossOrigin(origins = "http://localhost:4200") // Autoriser Angular

public class DossierController {

    @Autowired
    private DossierService dossierService;

    @PostMapping
    public DossierDTO createDossier(@Validated @RequestBody DossierDTO dossierDTO) {
        return dossierService.createDossier(dossierDTO);
    }

    @GetMapping("/{id}")
    public DossierDTO getDossierById(@PathVariable Long id) {
        return dossierService.getDossierById(id);
    }

    @GetMapping
    public List<DossierDTO> getAllDossiers() {
        return dossierService.getAllDossiers();
    }

    @PutMapping("/{id}")
    public DossierDTO updateDossier(@PathVariable Long id, @RequestBody DossierDTO dossierDTO) {
        return dossierService.updateDossier(id, dossierDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDossier(@PathVariable Long id) {
        dossierService.deleteDossier(id);
    }
}
