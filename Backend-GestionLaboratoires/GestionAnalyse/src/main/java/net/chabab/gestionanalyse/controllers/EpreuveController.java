package net.chabab.gestionanalyse.controllers;

import net.chabab.gestionanalyse.dtos.EpreuveDTO;
import net.chabab.gestionanalyse.service.EpreuveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/epreuves")
public class EpreuveController {

    @Autowired
    private EpreuveService epreuveService;

    @PostMapping
    public EpreuveDTO createEpreuve(@RequestBody EpreuveDTO epreuveDTO) {
        return epreuveService.createEpreuve(epreuveDTO);
    }

    @GetMapping("/{id}")
    public EpreuveDTO getEpreuveById(@PathVariable Long id) {
        return epreuveService.getEpreuveById(id);
    }

    @GetMapping
    public List<EpreuveDTO> getAllEpreuves() {
        return epreuveService.getAllEpreuves();
    }

    @GetMapping("/by-analyse/{analyseId}")
    public List<EpreuveDTO> getEpreuvesByAnalyseId(@PathVariable Long analyseId) {
        return epreuveService.getEpreuvesByAnalyseId(analyseId);
    }

    @PutMapping("/{id}")
    public EpreuveDTO updateEpreuve(@PathVariable Long id, @RequestBody EpreuveDTO epreuveDTO) {
        return epreuveService.updateEpreuve(id, epreuveDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEpreuve(@PathVariable Long id) {
        epreuveService.deleteEpreuve(id);
    }
}
