package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.AdresseDTO;
import net.chabab.laboratoireservice.entities.Adresse;
import net.chabab.laboratoireservice.mapper.AdresseMapper;
import net.chabab.laboratoireservice.repository.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdresseService {

    @Autowired
    private AdresseRepository adresseRepository;

    // Créer une nouvelle adresse
    public AdresseDTO createAdresse(AdresseDTO adresseDTO) {
        Adresse adresse = AdresseMapper.toAdresseEntity(adresseDTO);
        Adresse savedAdresse = adresseRepository.save(adresse);
        return AdresseMapper.toAdresseDTO(savedAdresse);
    }

    // Obtenir une adresse par son ID
    public AdresseDTO getAdresseById(Long id) {
        Adresse adresse = adresseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adresse not found"));
        return AdresseMapper.toAdresseDTO(adresse);
    }

    // Obtenir toutes les adresses
    public List<AdresseDTO> getAllAdresses() {
        List<Adresse> adresses = adresseRepository.findAll();
        return adresses.stream()
                .map(AdresseMapper::toAdresseDTO)
                .collect(Collectors.toList());
    }

    // Mettre à jour une adresse
    public AdresseDTO updateAdresse(Long id, AdresseDTO adresseDTO) {
        Adresse existingAdresse = adresseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adresse not found"));

        existingAdresse.setNumVoie(adresseDTO.getNumVoie());
        existingAdresse.setNomVoie(adresseDTO.getNomVoie());
        existingAdresse.setCodePostal(adresseDTO.getCodePostal());
        existingAdresse.setVille(adresseDTO.getVille());
        existingAdresse.setCommune(adresseDTO.getCommune());

        Adresse updatedAdresse = adresseRepository.save(existingAdresse);
        return AdresseMapper.toAdresseDTO(updatedAdresse);
    }

    // Supprimer une adresse
    public void deleteAdresse(Long id) {
        adresseRepository.deleteById(id);
    }
}
