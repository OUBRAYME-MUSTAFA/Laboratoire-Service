package com.example.laboratoireservice.RestController;

import com.example.laboratoireservice.MODEL.Chercheur;
import com.example.laboratoireservice.MODEL.Equipe;
import com.example.laboratoireservice.entities.Axe;
import com.example.laboratoireservice.entities.Labo;
import com.example.laboratoireservice.feign.ChercheurRestClient;
import com.example.laboratoireservice.feign.EquipeRestClient;
import com.example.laboratoireservice.repository.AxeRepository;
import com.example.laboratoireservice.repository.LaboRepository;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@EnableFeignClients
@CrossOrigin("*")
public class LaboController
{

    private AxeRepository axeRepository;
    private LaboRepository laboRepository;
    private ChercheurRestClient chercheurRestClient;
    private EquipeRestClient equipeRestClient;

    public LaboController(AxeRepository axeRepository, LaboRepository laboRepository, ChercheurRestClient chercheurRestClient, EquipeRestClient equipeRestClient) {
        this.axeRepository = axeRepository;
        this.laboRepository = laboRepository;
        this.chercheurRestClient = chercheurRestClient;
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
        Chercheur chercheur = chercheurRestClient.getChercheurById(labo.getResponsableId());
        labo.setResponsable(chercheur);
        labo.getAxes().forEach(pi->{
            Axe axe = axeRepository.getAxeById(pi.getId());
            pi.setAxeName(axe.getName());
            pi.setLabos(null);
        });
        labo.getMember().forEach(ch->{
            Chercheur chercheur1 = chercheurRestClient.getChercheurById(ch.getId());
            ch.setNom(chercheur1.getNom());
            ch.setPrenom(chercheur1.getPrenom());

            ch.setLabos(null);
        });

        labo.getEquipesID().forEach(pi->{

            Equipe equipe = equipeRestClient.getEquipeById(pi);
            if(equipe.getLaboID() == labo.getId()) {
                equipe.setLabo(null);
                labo.getEquipes_object().add(equipe);
            }

        });
//        labo.getAxes().forEach(pi->{
//            Axe axe = axeRepository.getAxeById(pi.getId());
//            pi.setAxeName(axe.getName());
//        });
//        labo.getEquipesID().forEach(pi->{
//            Equipe equipe = equipeRestClient.getEquipeById(pi);
//            labo.getEquipes_object().add(equipe);
//        });

        return labo;
    }

    @GetMapping(path = "/fullLabos")
    public List<Labo>  getFullLabos() {

        List<Labo> list__ = laboRepository.findAll();
       // List mList = new ArrayList<>();

        list__.forEach(labo -> {
            Chercheur chercheur = chercheurRestClient.getChercheurById(labo.getResponsableId());
            chercheur.setId(labo.getResponsableId());
            labo.setResponsable(chercheur);
          //  mList.addAll(labo.getMember());
            labo.getAxes().forEach(pi->{
                Axe axe = axeRepository.getAxeById(pi.getId());
                pi.setAxeName(axe.getName());
                pi.setLabos(null);
            });
            labo.getMember().forEach(ch->{
                Chercheur chercheur1 = chercheurRestClient.getChercheurById(ch.getId());
                ch.setNom(chercheur1.getNom());
                ch.setPrenom(chercheur1.getPrenom());
                ch.setLabos(null);
            });

            labo.getEquipesID().forEach(pi->{
                Equipe equipe = equipeRestClient.getEquipeById(pi);
                if(equipe.getLaboID() == labo.getId()) {
                    equipe.setLabo(null);
                    labo.getEquipes_object().add(equipe);
                }

            });

            labo.getEquipes_object().forEach(ep->{

                ep.getMember().forEach(mem->{
                    if (! IdsMemberLabo(labo).contains(mem.getId()))
                        labo.getMember().add(mem);
                });
            });


        });
        return list__;
    }

public List IdsMemberLabo(Labo labo )
{
    List ids = new ArrayList<>();
    labo.getMember().forEach(mem ->{
        ids.add(mem.getId());
    });
    return ids;
}


@PostMapping("addLabo")
public ResponseEntity<Labo> addLabo(@RequestBody Labo labo){
    Chercheur chercheur = chercheurRestClient.getChercheurById(labo.getResponsable().getId());
    chercheur.setId(labo.getResponsableId());
    System.out.println("we sent id = "+labo.getResponsable().getId()+"************* we get chercheur id = "+chercheur.getId());
    Labo labo1 =new Labo(labo.getId(),labo.getAcro_labo(), labo.getIntitule(),labo.getResponsable().getId());
    laboRepository.save(labo1);
    labo.getAxes().forEach(pi->{
        System.out.println("********************** axes process is beging" );

        addAxe(axeRepository.findById(pi.getId()).get(),labo1.getId());
        System.out.println("********************** axes process is end" );

    });
    labo.getMember().forEach(member->{
        System.out.println("********************** members process is beging" );
        Chercheur newChercheur =chercheurRestClient.getChercheurById(member.getId());
        newChercheur.setId(member.getId());
        addMember(newChercheur,labo1.getId());
        System.out.println("********************** members process is end" );

    });
    labo.getEquipes_object().forEach(ep->{
        addEquipe(ep , labo1.getId());
//        labo1.getEquipes_ID().add(ep.getId());
//        System.out.println("******* equipe : "+ep.getAcro_equipe()+" was saved ");
//        equipeRestClient.addLabo(labo1 , ep.getId());
//        laboRepository.save(labo1);
    });
    return   new ResponseEntity<>(null, HttpStatus.CREATED);
}
//    @PostMapping("addLabo")
//    public Labo addLabo(@RequestBody Labo labo){
//        Chercheur chercheur = chercheurRestClient.getChercheurByName(labo.getResponsable().getName());
//        Labo labo1 =new Labo(labo.getId(),labo.getAcro_labo(), labo.getIntitule(),chercheur.getId());
//        laboRepository.save(labo1);
//        labo.getAxes().forEach(pi->{
//            addAxe(axeRepository.findById(pi.getId()).get(),labo1.getId());
//
//        });
//        return   labo;
//    }



    @PutMapping("update")
    public ResponseEntity<Labo> updateLabo(@RequestBody Labo labo){
//        Chercheur chercheur = chercheurRestClient.getChercheurByName(labo.getResponsable().getName());
//        labo.setResponsableId(chercheur.getId());
//        return laboRepository.save(labo);
        return addLabo(labo);

    }

    @PutMapping("labo/addAxe/{id}")
    public void addAxe(@RequestBody Axe axe, @PathVariable long id) {
        Labo labo =  laboRepository.findById(id).get();
        labo.addAxe(axe);
        laboRepository.save(labo);

    }

    @PutMapping("labo/addMember/{id}")
    public Labo addMember(@RequestBody Chercheur chercheur, @PathVariable long id) {
        Labo labo = laboRepository.findById(id).get();


        labo.addMember(chercheur);
        laboRepository.save(labo);
        return getFullLabo(labo.getId());
    }

    @PutMapping("addEquipe/{id}")
    public Labo addEquipe(@RequestBody Equipe equipe, @PathVariable long id) {
        Labo labo = laboRepository.findById(id).get();
        //Axe newAxe = axeRepository.findById(axe.getId()).get()
       labo.getEquipes_ID().add(equipe.getId());
        equipeRestClient.addLabo(labo , equipe.getId());

       laboRepository.save(labo);
      // equipeRestClient.addLabo(labo,equipe.getId());
        return getFullLabo(labo.getId());
    }

    @DeleteMapping(path = "/labos/{code}")
    public void deleteLabo(@PathVariable (name = "code") long code ) {
        Labo labo = laboRepository.findById(code).get();
        labo.getEquipes_ID().forEach(id->{
            equipeRestClient.deleteLabo(id);
        });
        laboRepository.deleteById(code);
}

    @PutMapping(path = "/labos/{id}/deleteEquipe/{code}")
    public void deleteEquipe(@PathVariable (name = "id") long id , @PathVariable (name = "code") long code ) {

        Labo labo = laboRepository.findById(id).get();

        labo.getEquipes_ID().remove(code);
        laboRepository.save(labo);

    }



}
