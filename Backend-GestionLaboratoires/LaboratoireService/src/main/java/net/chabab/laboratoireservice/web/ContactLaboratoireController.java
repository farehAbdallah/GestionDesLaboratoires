package net.chabab.laboratoireservice.web;

import net.chabab.laboratoireservice.dtos.ContactLaboratoireDTO;
import net.chabab.laboratoireservice.service.ContactLaboratoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactLaboratoireController {

    @Autowired
    private ContactLaboratoireService contactLaboratoireService;

    @PostMapping
    public ContactLaboratoireDTO createContact(@RequestBody ContactLaboratoireDTO contactDTO) {
        return contactLaboratoireService.createContact(contactDTO);
    }

    @GetMapping("/{id}")
    public ContactLaboratoireDTO getContactById(@PathVariable Long id) {
        return contactLaboratoireService.getContactById(id);
    }

    @GetMapping
    public List<ContactLaboratoireDTO> getAllContacts() {
        return contactLaboratoireService.getAllContacts();
    }

    @PutMapping("/{id}")
    public ContactLaboratoireDTO updateContact(@PathVariable Long id, @RequestBody ContactLaboratoireDTO contactDTO) {
        return contactLaboratoireService.updateContact(id, contactDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteContact(@PathVariable Long id) {
        return contactLaboratoireService.deleteContact(id);
    }
}
