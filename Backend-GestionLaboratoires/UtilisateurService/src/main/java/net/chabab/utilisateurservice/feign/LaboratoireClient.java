package net.chabab.utilisateurservice.feign;

import net.chabab.utilisateurservice.dtos.LaboratoireDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "laboratoire-service", url = "http://localhost:8085/laboratoires")
public interface LaboratoireClient {
    @GetMapping("/{id}")
    LaboratoireDTO getLaboratoireById(@PathVariable("id") Long id);
}


