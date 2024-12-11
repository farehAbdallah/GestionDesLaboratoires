package net.chabab.gestionanalyse;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class GestionAnalyseApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionAnalyseApplication.class, args);
    }




}
