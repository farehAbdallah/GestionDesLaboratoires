package net.chabab.gestionanalyse.controllers;

import net.chabab.gestionanalyse.dtos.AnalyseDTO;
import net.chabab.gestionanalyse.service.AnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analyses")
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
}
