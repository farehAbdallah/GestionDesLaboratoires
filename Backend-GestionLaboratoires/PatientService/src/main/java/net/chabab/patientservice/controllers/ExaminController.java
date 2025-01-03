package net.chabab.patientservice.controllers;

import net.chabab.patientservice.dtos.ExaminDTO;
import net.chabab.patientservice.services.ExaminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examins")
@CrossOrigin(origins = "http://localhost:4200") // Autoriser Angular

public class ExaminController {

    @Autowired
    private ExaminService examinService;

    // Create Examin
    @PostMapping
    public ExaminDTO createExamin(@RequestBody ExaminDTO examinDTO) {
        return examinService.createExamin(examinDTO);
    }

    // Get Examin by ID
    @GetMapping("/{id}")
    public ExaminDTO getExaminById(@PathVariable Long id) {
        return examinService.getExaminById(id);
    }

    // Get All Examins
    @GetMapping
    public List<ExaminDTO> getAllExamins() {
        return examinService.getAllExamins();
    }

    // Delete Examin by ID
    @DeleteMapping("/{id}")
    public void deleteExamin(@PathVariable Long id) {
        examinService.deleteExamin(id);
    }
}
