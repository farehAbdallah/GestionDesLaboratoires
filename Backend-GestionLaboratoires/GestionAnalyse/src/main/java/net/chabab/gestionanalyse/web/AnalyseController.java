package net.chabab.gestionanalyse.web;

import jakarta.validation.Valid;
import net.chabab.gestionanalyse.dtos.AnalyseDTO;
import net.chabab.gestionanalyse.service.AnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analyses")
public class AnalyseController {

    @Autowired
    private AnalyseService analyseService;

    // Créer une nouvelle analyse
    @PostMapping
    public AnalyseDTO create(@Valid @RequestBody AnalyseDTO analyseDTO) {
        return analyseService.createAnalyse(analyseDTO);
    }

    // Récupérer une analyse par son ID
    @GetMapping("/{id}")
    public AnalyseDTO getById(@PathVariable Long id) {
        return analyseService.getAnalyseById(id);
    }

    // Récupérer toutes les analyses
    @GetMapping
    public List<AnalyseDTO> getAll() {
        return analyseService.getAllAnalyses();
    }

    // Mettre à jour une analyse
    @PutMapping("/{id}")
    public AnalyseDTO update(@PathVariable Long id, @RequestBody AnalyseDTO analyseDTO) {
        return analyseService.updateAnalyse(id, analyseDTO);
    }

    // Supprimer une analyse
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        analyseService.deleteAnalyse(id);
    }
}
