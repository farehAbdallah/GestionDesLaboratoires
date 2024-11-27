package net.chabab.gestionanalyse.web;

import net.chabab.gestionanalyse.dtos.EpreuveDTO;
import net.chabab.gestionanalyse.service.EpreuveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/epreuves")
public class EpreuveController {

    @Autowired
    private EpreuveService epreuveService;

    // Créer une nouvelle épreuve
    @PostMapping
    public EpreuveDTO create(@RequestBody EpreuveDTO epreuveDTO) {
        return epreuveService.createEpreuve(epreuveDTO);
    }

    // Récupérer une épreuve par son ID
    @GetMapping("/{id}")
    public EpreuveDTO getById(@PathVariable Long id) {
        return epreuveService.getEpreuveById(id);
    }

    // Récupérer toutes les épreuves
    @GetMapping
    public List<EpreuveDTO> getAll() {
        return epreuveService.getAllEpreuves();
    }

    // Mettre à jour une épreuve
    @PutMapping("/{id}")
    public EpreuveDTO update(@PathVariable Long id, @RequestBody EpreuveDTO epreuveDTO) {
        return epreuveService.updateEpreuve(id, epreuveDTO);
    }

    // Supprimer une épreuve
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        epreuveService.deleteEpreuve(id);
    }
}
