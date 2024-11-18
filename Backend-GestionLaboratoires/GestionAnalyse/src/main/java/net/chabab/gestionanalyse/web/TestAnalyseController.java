package net.chabab.gestionanalyse.web;

import net.chabab.gestionanalyse.dtos.TestAnalyseDTO;
import net.chabab.gestionanalyse.service.TestAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testanalyses")
public class TestAnalyseController {

    @Autowired
    private TestAnalyseService testAnalyseService;

    // Créer un nouveau TestAnalyse
    @PostMapping
    public TestAnalyseDTO create(@RequestBody TestAnalyseDTO testAnalyseDTO) {
        return testAnalyseService.createTestAnalyse(testAnalyseDTO);
    }

    // Récupérer un TestAnalyse par son ID
    @GetMapping("/{id}")
    public TestAnalyseDTO getById(@PathVariable Long id) {
        return testAnalyseService.getTestAnalyseById(id);
    }

    // Récupérer tous les TestAnalyses
    @GetMapping
    public List<TestAnalyseDTO> getAll() {
        return testAnalyseService.getAllTestAnalyses();
    }

    // Mettre à jour un TestAnalyse
    @PutMapping("/{id}")
    public TestAnalyseDTO update(@PathVariable Long id, @RequestBody TestAnalyseDTO testAnalyseDTO) {
        return testAnalyseService.updateTestAnalyse(id, testAnalyseDTO);
    }

    // Supprimer un TestAnalyse
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        testAnalyseService.deleteTestAnalyse(id);
    }
}
