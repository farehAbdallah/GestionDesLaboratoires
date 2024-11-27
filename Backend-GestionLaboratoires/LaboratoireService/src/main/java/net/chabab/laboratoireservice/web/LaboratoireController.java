package net.chabab.laboratoireservice.web;

import net.chabab.laboratoireservice.dtos.LaboratoireDTO;
import net.chabab.laboratoireservice.service.LaboratoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratoires")
public class LaboratoireController {

    @Autowired
    private LaboratoireService laboratoireService;

    // Créer un nouveau laboratoire
    @PostMapping
    public LaboratoireDTO create(@RequestBody LaboratoireDTO laboratoireDTO) {
        return laboratoireService.createLaboratoire(laboratoireDTO);
    }

    // Récupérer un laboratoire par son ID
    @GetMapping("/{id}")
    public LaboratoireDTO getById(@PathVariable Long id) {
        return laboratoireService.getLaboratoireById(id);
    }

    // Récupérer tous les laboratoires
    @GetMapping
    public List<LaboratoireDTO> getAll() {
        return laboratoireService.getAllLaboratoires();
    }

    // Mettre à jour un laboratoire
    @PutMapping("/{id}")
    public LaboratoireDTO update(@PathVariable Long id, @RequestBody LaboratoireDTO laboratoireDTO) {
        return laboratoireService.updateLaboratoire(id, laboratoireDTO);
    }

    // Supprimer un laboratoire
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        laboratoireService.deleteLaboratoire(id);
    }
}
