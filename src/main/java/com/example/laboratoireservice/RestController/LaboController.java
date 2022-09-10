package com.example.laboratoireservice.RestController;

import com.example.laboratoireservice.MODEL.Equipe;
import com.example.laboratoireservice.entities.Axe;
import com.example.laboratoireservice.entities.Labo;
import com.example.laboratoireservice.feign.ChercheurRestCient;
import com.example.laboratoireservice.feign.EquipeRestClient;
import com.example.laboratoireservice.repository.AxeRepository;
import com.example.laboratoireservice.repository.LaboRepository;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@EnableFeignClients
public class LaboController
{

    private AxeRepository axeRepository;
    private LaboRepository laboRepository;
    private ChercheurRestCient chercheurRestCient;
    private EquipeRestClient equipeRestClient;

    public LaboController(AxeRepository axeRepository, LaboRepository laboRepository, ChercheurRestCient chercheurRestCient, EquipeRestClient equipeRestClient) {
        this.axeRepository = axeRepository;
        this.laboRepository = laboRepository;
        this.chercheurRestCient = chercheurRestCient;
        this.equipeRestClient = equipeRestClient;
    }

    @GetMapping(path = "/labos")
    public List<Labo> listLabos()
    {
        return laboRepository.findAll();
    }

    @GetMapping(path = "/labos/{id}")
    public  Labo getLabo(@PathVariable(name = "id") Long id)
    {
        Labo labo = laboRepository.findById(id).get();
        return labo;
    }

    @GetMapping(path = "/fullLabo/{id}")
    public Labo getFullLabo(@PathVariable(name = "id") Long id){
        Labo labo = laboRepository.findById(id).get();
        //Customer customer = customerRestClient.getCustomerById(bill.getCustomerID());
        //equipe.setCustomer(customer);
        labo.getAxe_list().forEach(pi->{
            Axe axe = axeRepository.getAxeById(pi.getId());
            //pi.setProduct(product);
            pi.setAxeName(axe.getName());
        });
        return labo;
    }

    @PostMapping("addLabo")
    public Labo addLabo(@RequestBody Labo labo){
        //labo.setResponsable(chercheurRestCient.getChercheurById(labo.getResponsable().getId()));
        labo.setResponsable(chercheurRestCient.getChercheurByName(labo.getResponsable().getName()));
        return laboRepository.save(labo);

    }

    @PutMapping("labo/addAxe/{id}")
    public Labo addAxe(@RequestBody Axe axe, @PathVariable long id) {
        Labo labo = laboRepository.findById(id).get();
        //Axe newAxe = axeRepository.findById(axe.getId()).get()

        Long IdAxe = axeRepository.findByName(axe.getName()).getId();
        Axe newAxe = axeRepository.findById(IdAxe).get();

        labo.getAxe_list().add(newAxe);
        newAxe.getLabo_list().add(labo);


        laboRepository.save(labo);
        axeRepository.save(newAxe);
        return labo;
    }

    @PutMapping("labo/addEquipe/{id}")
    public Labo addEquipe(@RequestBody Equipe equipe, @PathVariable long id) {
        Labo labo = laboRepository.findById(id).get();
        //Axe newAxe = axeRepository.findById(axe.getId()).get()

       labo.getEquipe_list().add(equipeRestClient.getEquipeById(equipe.getId()));



        laboRepository.save(labo);
        return labo;
    }
}
