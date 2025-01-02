package net.chabab.laboratoireservice.web;

import net.chabab.laboratoireservice.dtos.LaboratoireDTO;
import net.chabab.laboratoireservice.entities.Adresse;
import net.chabab.laboratoireservice.entities.ContactLaboratoire;
import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.service.LaboratoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.chabab.laboratoireservice.service.LaboratoireService;  // Assurez-vous que cette importation est présente

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

    @PostMapping("/send-data")
    public String sendLaboratoireData(@RequestBody Laboratoire laboratoire,
                                      @RequestBody Adresse adresse,
                                      @RequestBody ContactLaboratoire contactLaboratoire) {
        // Envoie les informations au Kafka Producer
        laboratoireService.sendLaboratoireDetails(laboratoire, adresse, contactLaboratoire);
        return "Données envoyées au Kafka!";
    }

}
