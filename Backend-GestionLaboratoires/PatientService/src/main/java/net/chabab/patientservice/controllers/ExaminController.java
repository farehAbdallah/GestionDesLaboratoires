package net.chabab.patientservice.controllers;

import net.chabab.patientservice.dtos.ExaminDTO;
import net.chabab.patientservice.services.ExaminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examins")
public class ExaminController {

    @Autowired
    private ExaminService examinService;

    @PostMapping
    public ExaminDTO createExamin(@RequestBody ExaminDTO examinDTO) {
        return examinService.createExamin(examinDTO);
    }

    @GetMapping("/{id}")
    public ExaminDTO getExaminById(@PathVariable Long id) {
        return examinService.getExaminById(id);
    }

    @GetMapping
    public List<ExaminDTO> getAllExamins() {
        return examinService.getAllExamins();
    }

    @DeleteMapping("/{id}")
    public void deleteExamin(@PathVariable Long id) {
        examinService.deleteExamin(id);
    }
}
