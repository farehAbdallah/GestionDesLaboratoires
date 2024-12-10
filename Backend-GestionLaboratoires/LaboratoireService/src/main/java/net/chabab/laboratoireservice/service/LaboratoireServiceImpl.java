package net.chabab.laboratoireservice.service;

import net.chabab.laboratoireservice.dtos.LaboratoireDTO;
import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.mapper.LaboratoireMapper;
import net.chabab.laboratoireservice.repository.LaboratoireRepository;
import net.chabab.laboratoireservice.service.LaboratoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaboratoireServiceImpl implements LaboratoireService {

    @Autowired
    private LaboratoireRepository laboratoireRepository;

    @Override
    public LaboratoireDTO createLaboratoire(LaboratoireDTO laboratoireDTO) {
        Laboratoire laboratoire = LaboratoireMapper.INSTANCE.toEntity(laboratoireDTO);
        Laboratoire savedLaboratoire = laboratoireRepository.save(laboratoire);
        return LaboratoireMapper.INSTANCE.toDto(savedLaboratoire);
    }

    @Override
    public LaboratoireDTO getLaboratoireById(Long id) {
        Laboratoire laboratoire = laboratoireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laboratoire not found with id: " + id));
        return LaboratoireMapper.INSTANCE.toDto(laboratoire);
    }

    @Override
    public List<LaboratoireDTO> getAllLaboratoires() {
        return laboratoireRepository.findAll().stream()
                .map(LaboratoireMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LaboratoireDTO updateLaboratoire(Long id, LaboratoireDTO laboratoireDTO) {
        Laboratoire existingLaboratoire = laboratoireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laboratoire not found with id: " + id));

        existingLaboratoire.setNrc(laboratoireDTO.getNrc());
        existingLaboratoire.setNom(laboratoireDTO.getNom());
        existingLaboratoire.setLogo(laboratoireDTO.getLogo());
        existingLaboratoire.setActive(laboratoireDTO.isActive());
        existingLaboratoire.setDateActivation(laboratoireDTO.getDateActivation());

        Laboratoire updatedLaboratoire = laboratoireRepository.save(existingLaboratoire);
        return LaboratoireMapper.INSTANCE.toDto(updatedLaboratoire);
    }

    @Override
    public boolean deleteLaboratoire(Long id) {
        if (!laboratoireRepository.existsById(id)) {
            throw new RuntimeException("Laboratoire not found with id: " + id);
        }
        laboratoireRepository.deleteById(id);
        return true;
    }
}
