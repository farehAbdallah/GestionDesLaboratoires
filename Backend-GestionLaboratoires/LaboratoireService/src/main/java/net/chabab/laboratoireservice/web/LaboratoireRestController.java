package net.chabab.laboratoireservice.web;

import net.chabab.laboratoireservice.entities.Laboratoire;
import net.chabab.laboratoireservice.repository.LaboratoireRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LaboratoireRestController {

    private LaboratoireRepository laboratoireRepository;

    public LaboratoireRestController(LaboratoireRepository laboratoireRepository) {
        this.laboratoireRepository = laboratoireRepository;
    }
    @GetMapping("/laboratoires")
    public List<Laboratoire> laboratoireList(){
        return laboratoireRepository.findAll();
    }
    @GetMapping("/laboratoires/{id}")
    public Laboratoire laboratoireById(@PathVariable Long id){
        return laboratoireRepository.findById(id).get();
    }
}
