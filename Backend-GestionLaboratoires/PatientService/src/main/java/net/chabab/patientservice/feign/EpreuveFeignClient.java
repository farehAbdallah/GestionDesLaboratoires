package net.chabab.patientservice.feign;

import net.chabab.patientservice.dtos.EpreuveDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gestion-analyse-service", url = "http://localhost:8083/api/epreuves")  // URL du service GestionAnalyseService
public interface EpreuveFeignClient {

    @GetMapping("/{id}")
    EpreuveDTO getEpreuveById(@PathVariable("id") Long id);
}


