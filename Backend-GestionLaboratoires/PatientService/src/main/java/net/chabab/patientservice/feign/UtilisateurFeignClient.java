package net.chabab.patientservice.feign;

import net.chabab.patientservice.dtos.UtilisateurDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "utilisateur-service", url = "http://utilisateur-service/api/utilisateurs")
public interface UtilisateurFeignClient {

    @GetMapping("/validate-email")
    boolean isEmailValid(@RequestParam String email);

    @GetMapping("/email/{email}")
    UtilisateurDTO getUtilisateurByEmail(@PathVariable String email);
}
