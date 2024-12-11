package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.AdresseDTO;
import net.chabab.laboratoireservice.entities.Adresse;
import net.chabab.laboratoireservice.mapper.AdresseMapper;
import net.chabab.laboratoireservice.repository.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdresseServiceImpl implements AdresseService {

    @Autowired
    private AdresseRepository adresseRepository;

    @Override
    public AdresseDTO createAdresse(AdresseDTO adresseDTO) {
        Adresse adresse = AdresseMapper.INSTANCE.toEntity(adresseDTO);
        Adresse savedAdresse = adresseRepository.save(adresse);
        return AdresseMapper.INSTANCE.toDto(savedAdresse);
    }

    @Override
    public AdresseDTO getAdresseById(Long id) {
        Adresse adresse = adresseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adresse not found with id: " + id));
        return AdresseMapper.INSTANCE.toDto(adresse);
    }

    @Override
    public List<AdresseDTO> getAllAdresses() {
        return adresseRepository.findAll().stream()
                .map(AdresseMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdresseDTO updateAdresse(Long id, AdresseDTO adresseDTO) {
        Adresse existingAdresse = adresseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adresse not found with id: " + id));

        existingAdresse.setNumVoie(adresseDTO.getNumVoie());
        existingAdresse.setNomVoie(adresseDTO.getNomVoie());
        existingAdresse.setCodePostal(adresseDTO.getCodePostal());
        existingAdresse.setVille(adresseDTO.getVille());
        existingAdresse.setCommune(adresseDTO.getCommune());

        Adresse updatedAdresse = adresseRepository.save(existingAdresse);
        return AdresseMapper.INSTANCE.toDto(updatedAdresse);
    }

    @Override
    public boolean deleteAdresse(Long id) {
        if (!adresseRepository.existsById(id)) {
            throw new RuntimeException("Adresse not found with id: " + id);
        }
        adresseRepository.deleteById(id);
        return true;
    }
}
