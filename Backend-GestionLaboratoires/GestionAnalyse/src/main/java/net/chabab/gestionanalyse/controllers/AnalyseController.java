package net.chabab.gestionanalyse.controllers;

import net.chabab.gestionanalyse.dtos.AnalyseDTO;
import net.chabab.gestionanalyse.entities.Analyse;
import net.chabab.gestionanalyse.entities.Epreuve;
import net.chabab.gestionanalyse.entities.TestAnalyse;
import net.chabab.gestionanalyse.service.AnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analyses")
@CrossOrigin(origins = "http://localhost:4200") // Autoriser Angular

public class AnalyseController {

    @Autowired
    private AnalyseService analyseService;

    @PostMapping
    public AnalyseDTO createAnalyse(@RequestBody AnalyseDTO analyseDTO) {
        return analyseService.createAnalyse(analyseDTO);
    }

    @GetMapping("/{id}")
    public AnalyseDTO getAnalyseById(@PathVariable Long id) {
        return analyseService.getAnalyseById(id);
    }

    @GetMapping
    public List<AnalyseDTO> getAllAnalyses() {
        return analyseService.getAllAnalyses();
    }

    @PutMapping("/{id}")
    public AnalyseDTO updateAnalyse(@PathVariable Long id, @RequestBody AnalyseDTO analyseDTO) {
        return analyseService.updateAnalyse(id, analyseDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAnalyse(@PathVariable Long id) {
        analyseService.deleteAnalyse(id);
    }

    @PostMapping("/send-data")
    public String sendAnalyseData(@RequestBody Analyse analyse,
                                  @RequestBody TestAnalyse testAnalyse,
                                  @RequestBody Epreuve epreuve) {
        // Appel de la méthode qui envoie les données via Kafka
        analyseService.sendAnalyseDetails(analyse, testAnalyse, epreuve);
        return "Données envoyées au Kafka pour traitement.";
    }
}
