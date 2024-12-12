package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.AdresseDTO;

import java.util.List;

public interface AdresseService {
    AdresseDTO createAdresse(AdresseDTO adresseDTO);
    AdresseDTO getAdresseById(Long id);
    List<AdresseDTO> getAllAdresses();
    AdresseDTO updateAdresse(Long id, AdresseDTO adresseDTO);
    boolean deleteAdresse(Long id);
}
