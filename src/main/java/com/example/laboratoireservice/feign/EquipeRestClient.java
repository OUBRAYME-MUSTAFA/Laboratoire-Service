package com.example.laboratoireservice.feign;

import com.example.laboratoireservice.MODEL.Chercheur;
import com.example.laboratoireservice.MODEL.Equipe;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "EQUIPE-SERVICE")
public interface EquipeRestClient {
    @GetMapping(path = "/equipes/{id}")
    public Equipe getEquipeById(@PathVariable("id")Long id);
    @GetMapping(path = "/getEquipe/{name}")
    Equipe getEquipeByName(@PathVariable("name")String name);
}
