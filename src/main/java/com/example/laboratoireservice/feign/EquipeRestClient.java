package com.example.laboratoireservice.feign;

import com.example.laboratoireservice.MODEL.Equipe;
import com.example.laboratoireservice.entities.Labo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "EQUIPE-SERVICE")
public interface EquipeRestClient {
    @GetMapping(path = "/equipes/{id}")
    public Equipe getEquipeById(@PathVariable("id")Long id);
    @GetMapping(path = "/getEquipe/{name}")
    Equipe getEquipeByName(@PathVariable("name")String name);

    @PutMapping("addLabo/{id}")
    public Equipe addLabo(@RequestBody Labo labo, @PathVariable long id) ;

    @PutMapping("update")
    public ResponseEntity<Equipe> updateEquipe(@RequestBody Equipe equipe);

    @PutMapping("deleteLabo/{code}")
    void deleteLabo(@PathVariable  long code);

//    @PutMapping("addLabo/{id}")
//    public Equipe addLabo(@RequestBody Labo labo, @PathVariable long id);
}
