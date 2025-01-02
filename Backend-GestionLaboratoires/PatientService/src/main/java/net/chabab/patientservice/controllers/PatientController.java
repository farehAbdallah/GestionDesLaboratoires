package net.chabab.patientservice.controllers;

import net.chabab.patientservice.dtos.PatientDTO;
import net.chabab.patientservice.entities.Dossier;
import net.chabab.patientservice.entities.Examin;
import net.chabab.patientservice.entities.Patient;
import net.chabab.patientservice.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public PatientDTO createPatient(@RequestBody PatientDTO patientDTO) {
        return patientService.createPatient(patientDTO);
    }

    @GetMapping("/{id}")
    public PatientDTO getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PutMapping("/{id}")
    public PatientDTO updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        return patientService.updatePatient(id, patientDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
    // Endpoint pour recevoir les données du patient, dossier, et examen et les envoyer via Kafka
    @PostMapping("/send-data")
    public String sendPatientData(@RequestBody Patient patient,
                                  @RequestBody Dossier dossier,
                                  @RequestBody Examin examin) {
        // Appel de la méthode qui envoie les données via Kafka
        patientService.sendPatientDetails(patient, dossier, examin);
        return "Données envoyées au Kafka pour traitement.";
    }
}
