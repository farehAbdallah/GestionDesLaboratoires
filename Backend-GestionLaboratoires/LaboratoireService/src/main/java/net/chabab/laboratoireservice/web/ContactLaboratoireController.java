package net.chabab.laboratoireservice.web;

import net.chabab.laboratoireservice.dtos.ContactLaboratoireDTO;
import net.chabab.laboratoireservice.service.ContactLaboratoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactLaboratoireController {

    @Autowired
    private ContactLaboratoireService contactLaboratoireService;

    // Créer un nouveau contact
    @PostMapping
    public ContactLaboratoireDTO create(@RequestBody ContactLaboratoireDTO contactLaboratoireDTO) {
        return contactLaboratoireService.createContactLaboratoire(contactLaboratoireDTO);
    }

    // Récupérer un contact par son ID
    @GetMapping("/{id}")
    public ContactLaboratoireDTO getById(@PathVariable Long id) {
        return contactLaboratoireService.getContactLaboratoireById(id);
    }

    // Récupérer tous les contacts
    @GetMapping
    public List<ContactLaboratoireDTO> getAll() {
        return contactLaboratoireService.getAllContactsLaboratoire();
    }

    // Mettre à jour un contact
    @PutMapping("/{id}")
    public ContactLaboratoireDTO update(@PathVariable Long id, @RequestBody ContactLaboratoireDTO contactLaboratoireDTO) {
        return contactLaboratoireService.updateContactLaboratoire(id, contactLaboratoireDTO);
    }

    // Supprimer un contact
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contactLaboratoireService.deleteContactLaboratoire(id);
    }
}
