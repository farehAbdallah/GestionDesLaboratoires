package net.chabab.patientservice.dtos;

import java.util.List;

public class PatientKafkaDTO {
    private PatientDTO patient;
    private DossierDTO dossier;
    private List<ExaminDTO> examens;

    // Getters et Setters
    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public DossierDTO getDossier() {
        return dossier;
    }

    public void setDossier(DossierDTO dossier) {
        this.dossier = dossier;
    }

    public List<ExaminDTO> getExamens() {
        return examens;
    }

    public void setExamens(List<ExaminDTO> examens) {
        this.examens = examens;
    }
}
