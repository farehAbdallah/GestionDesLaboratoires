package net.chabab.laboratoireservice.web;

import net.chabab.laboratoireservice.dtos.AdresseDTO;
import net.chabab.laboratoireservice.service.AdresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adresses")
public class AdresseController {

    @Autowired
    private AdresseService adresseService;

    // Créer une nouvelle adresse
    @PostMapping
    public AdresseDTO create(@RequestBody AdresseDTO adresseDTO) {
        return adresseService.createAdresse(adresseDTO);
    }

    // Récupérer une adresse par son ID
    @GetMapping("/{id}")
    public AdresseDTO getById(@PathVariable Long id) {
        return adresseService.getAdresseById(id);
    }

    // Récupérer toutes les adresses
    @GetMapping
    public List<AdresseDTO> getAll() {
        return adresseService.getAllAdresses();
    }

    // Mettre à jour une adresse
    @PutMapping("/{id}")
    public AdresseDTO update(@PathVariable Long id, @RequestBody AdresseDTO adresseDTO) {
        return adresseService.updateAdresse(id, adresseDTO);
    }

    // Supprimer une adresse
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        adresseService.deleteAdresse(id);
    }
}
