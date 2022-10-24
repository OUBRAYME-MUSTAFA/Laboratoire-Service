package com.example.laboratoireservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@EnableFeignClients
@CrossOrigin("*")
public class LaboratoireServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =
                SpringApplication.run(LaboratoireServiceApplication.class, args);

}

}
