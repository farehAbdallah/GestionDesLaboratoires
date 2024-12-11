package net.chabab.laboratoireservice.web;

import net.chabab.laboratoireservice.dtos.LaboratoireDTO;
import net.chabab.laboratoireservice.service.LaboratoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laboratoires")
public class LaboratoireController {

    @Autowired
    private LaboratoireService laboratoireService;

    @PostMapping
    public LaboratoireDTO createLaboratoire(@RequestBody LaboratoireDTO laboratoireDTO) {
        return laboratoireService.createLaboratoire(laboratoireDTO);
    }

    @GetMapping
    public List<LaboratoireDTO> getAllLaboratoires() {
        return laboratoireService.getAllLaboratoires();
    }

    @GetMapping("/{id}")
    public LaboratoireDTO getLaboratoireById(@PathVariable Long id) {
        return laboratoireService.getLaboratoireById(id);
    }

    @PutMapping("/{id}")
    public LaboratoireDTO updateLaboratoire(@PathVariable Long id, @RequestBody LaboratoireDTO laboratoireDTO) {
        return laboratoireService.updateLaboratoire(id, laboratoireDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteLaboratoire(@PathVariable Long id) {
        return laboratoireService.deleteLaboratoire(id);
    }

}
