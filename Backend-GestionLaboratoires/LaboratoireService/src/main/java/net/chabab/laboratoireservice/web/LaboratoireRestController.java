package net.chabab.laboratoireservice.web;

import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.service.LaboratoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/laboratoires")
public class LaboratoireRestController {

    @Autowired
    private LaboratoireService laboratoireService;

    // Get all laboratories
    @GetMapping
    public List<Laboratoire> getAllLaboratoires() {
        return laboratoireService.getAllLaboratoires();
    }

    // Get a laboratory by ID
    @GetMapping("/{id}")
    public Optional<Laboratoire> getLaboratoireById(@PathVariable Long id) {
        return laboratoireService.getLaboratoireById(id);
    }

    // Create a new laboratory
    @PostMapping
    public Laboratoire createLaboratoire(@RequestBody Laboratoire laboratoire) {
        return laboratoireService.saveLaboratoire(laboratoire);
    }

    // Update an existing laboratory
    @PutMapping("/{id}")
    public Laboratoire updateLaboratoire(@PathVariable Long id, @RequestBody Laboratoire laboratoire) {
        return laboratoireService.updateLaboratoire(id, laboratoire);
    }

    // Delete a laboratory
    @DeleteMapping("/{id}")
    public void deleteLaboratoire(@PathVariable Long id) {
        laboratoireService.deleteLaboratoire(id);
    }
}
