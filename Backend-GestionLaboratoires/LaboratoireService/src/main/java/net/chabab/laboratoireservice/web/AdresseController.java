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

    @PostMapping
    public AdresseDTO createAdresse(@RequestBody AdresseDTO adresseDTO) {
        return adresseService.createAdresse(adresseDTO);
    }

    @GetMapping("/{id}")
    public AdresseDTO getAdresseById(@PathVariable Long id) {
        return adresseService.getAdresseById(id);
    }

    @GetMapping
    public List<AdresseDTO> getAllAdresses() {
        return adresseService.getAllAdresses();
    }

    @PutMapping("/{id}")
    public AdresseDTO updateAdresse(@PathVariable Long id, @RequestBody AdresseDTO adresseDTO) {
        return adresseService.updateAdresse(id, adresseDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAdresse(@PathVariable Long id) {
        return adresseService.deleteAdresse(id);
    }
}
