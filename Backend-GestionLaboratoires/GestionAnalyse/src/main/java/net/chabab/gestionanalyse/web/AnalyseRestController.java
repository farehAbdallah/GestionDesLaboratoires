package net.chabab.gestionanalyse.web;

import net.chabab.gestionanalyse.entites.Analyse;
import net.chabab.gestionanalyse.repository.AnalyseRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnalyseRestController {

    private AnalyseRepository analyseRepository;

    AnalyseRestController(AnalyseRepository analyseRepository){
        this.analyseRepository = analyseRepository;
    }


    @GetMapping("analyses/")
    public List<Analyse> analyseList() {
        return analyseRepository.findAll();
    }

    @GetMapping("analyses/{id}")
    public Analyse analyseById(@PathVariable Long id){
        return analyseRepository.findById(id).get();
    }
}
