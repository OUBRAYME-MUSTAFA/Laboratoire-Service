package com.example.laboratoireservice;

import com.example.laboratoireservice.entities.Labo;
import com.example.laboratoireservice.repository.LaboRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class LaboratoireServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =
                SpringApplication.run(LaboratoireServiceApplication.class, args);

//        LaboRepository laboRepository =
//                configurableApplicationContext.getBean(LaboRepository.class);
//        AxeRepository axeRepository =
//                configurableApplicationContext.getBean(AxeRepository.class);

//        Labo ep1 = new Labo( "acro1", "nom1", "rep1");
//        Labo ep2 = new Labo( "acro2", "nom2", "rep2") ;
//        List<Labo> ccc = Arrays.asList(ep1, ep2);
//        laboRepository.saveAll(ccc);

    }

}
