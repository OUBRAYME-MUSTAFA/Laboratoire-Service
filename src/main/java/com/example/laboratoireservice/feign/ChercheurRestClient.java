package com.example.laboratoireservice.feign;


import com.example.laboratoireservice.MODEL.Chercheur;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CHERCHEUR-SERVICE")
public interface ChercheurRestClient {
    @GetMapping(path = "/chercheurs/{id}")
    public Chercheur getChercheurById(@PathVariable("id")Long id);

    @GetMapping(path = "/getChercheurByName/{name}")
    Chercheur getChercheurByName(@PathVariable("name")String name);
    //@GetMapping(path = "/chercheur/{id}")
    //public List<Chercheur> getChercheurById(@PathVariable("id")Long id);
}
