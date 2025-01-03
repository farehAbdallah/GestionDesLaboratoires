package net.chabab.gestionanalyse.controllers;

import net.chabab.gestionanalyse.dtos.TestAnalyseDTO;
import net.chabab.gestionanalyse.service.TestAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-analyses")
@CrossOrigin(origins = "http://localhost:4200") // Autoriser Angular

public class TestAnalyseController {

    @Autowired
    private TestAnalyseService testAnalyseService;

    @PostMapping
    public TestAnalyseDTO createTestAnalyse(@RequestBody TestAnalyseDTO testAnalyseDTO) {
        return testAnalyseService.createTestAnalyse(testAnalyseDTO);
    }

    @GetMapping("/{id}")
    public TestAnalyseDTO getTestAnalyseById(@PathVariable Long id) {
        return testAnalyseService.getTestAnalyseById(id);
    }

    @GetMapping
    public List<TestAnalyseDTO> getAllTestAnalyses() {
        return testAnalyseService.getAllTestAnalyses();
    }

    @GetMapping("/analyse/{fkIdAnalyse}")
    public List<TestAnalyseDTO> getTestAnalysesByAnalyseId(@PathVariable Long fkIdAnalyse) {
        return testAnalyseService.getTestAnalysesByAnalyseId(fkIdAnalyse);
    }

    @PutMapping("/{id}")
    public TestAnalyseDTO updateTestAnalyse(@PathVariable Long id, @RequestBody TestAnalyseDTO testAnalyseDTO) {
        return testAnalyseService.updateTestAnalyse(id, testAnalyseDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTestAnalyse(@PathVariable Long id) {
        testAnalyseService.deleteTestAnalyse(id);
    }
}
