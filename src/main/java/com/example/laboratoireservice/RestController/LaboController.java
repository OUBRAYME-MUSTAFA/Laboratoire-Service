package com.example.laboratoireservice.RestController;

import com.example.laboratoireservice.MODEL.Chercheur;
import com.example.laboratoireservice.MODEL.Equipe;
import com.example.laboratoireservice.entities.Axe;
import com.example.laboratoireservice.entities.Labo;
import com.example.laboratoireservice.feign.ChercheurRestCient;
import com.example.laboratoireservice.feign.EquipeRestClient;
import com.example.laboratoireservice.repository.AxeRepository;
import com.example.laboratoireservice.repository.LaboRepository;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.Callable;


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
        Chercheur chercheur = chercheurRestCient.getChercheurById(labo.getResponsableId());
        labo.setResponsable(chercheur);
        labo.getAxes().forEach(pi->{
            Axe axe = axeRepository.getAxeById(pi.getId());
            pi.setAxeName(axe.getName());
        });
        labo.getEquipe_list().forEach(pi->{
            Equipe equipe = equipeRestClient.getEquipeById(pi);
            labo.getEquipe().add(equipe);
        });

        return labo;
    }

    @GetMapping(path = "/fullLabos")
    public List<Labo>  getFullLabos() {
        List<Labo> list__ = laboRepository.findAll();
        list__.forEach(labo -> {
            Chercheur chercheur = chercheurRestCient.getChercheurById(labo.getResponsableId());
            labo.setResponsable(chercheur);
            labo.getAxes().forEach(pi->{
                Axe axe = axeRepository.getAxeById(pi.getId());
                pi.setAxeName(axe.getName());
            });
            labo.getEquipe_list().forEach(pi->{
                Equipe equipe = equipeRestClient.getEquipeById(pi);
                labo.getEquipe().add(equipe);
            });

        });
        return list__;
    }


//    @PostMapping("addLabo")
//    public Labo addLabo(@RequestBody Labo labo){
//
//        Chercheur chercheur = chercheurRestCient.getChercheurByName(labo.getResponsable().getName());
//        labo.setResponsableId(chercheur.getId());
//        //labo.setResponsableId(labo.getResponsable().getId());//THIS IS WORKING
//
//
//
//       return laboRepository.save(labo);
//
//    }
@PostMapping("addLabo")
public Labo addLabo(@RequestBody Labo labo){
        Labo labo1 =new Labo(labo.getId(),labo.getAcro_labo(), labo.getIntitule(),labo.getResponsable().getId());
    laboRepository.save(labo1);
    ////THIS IS WORKING
   // labo.addAxe(labo.getAxes());
    System.out.println("*************************************SAVE DONE************************************************************");
    labo.getAxes().forEach(pi->{
        System.out.println("id"+pi.getId());
        System.out.println("name"+pi.getName());
        System.out.println("labo id"+labo1.getId());


        addAxe(axeRepository.findById(pi.getId()).get(),labo1.getId());

    });
//   axeRepository.saveAll(list);
    return   labo;
}


    @PutMapping("update")
    public Labo updateLabo(@RequestBody Labo labo){
        Chercheur chercheur = chercheurRestCient.getChercheurByName(labo.getResponsable().getName());
        labo.setResponsableId(chercheur.getId());
        return laboRepository.save(labo);

    }

    @PutMapping("labo/addAxe/{id}")
    public Labo addAxe(@RequestBody Axe axe, @PathVariable long id) {
        Labo labo =  laboRepository.findById(id).get();
        labo.addAxe(axe);
        laboRepository.save(labo);
        return labo;
    }

    @PutMapping("addEquipe/{id}")
    public Labo addEquipe(@RequestBody Equipe equipe, @PathVariable long id) {
        Labo labo = laboRepository.findById(id).get();
        //Axe newAxe = axeRepository.findById(axe.getId()).get()

       labo.getEquipe_list().add(equipe.getId());

       laboRepository.save(labo);
        return labo;
    }

    @DeleteMapping(path = "/labos/{code}")
    public void deleteLabo(@PathVariable (name = "code") long code ) {

        laboRepository.deleteById(code);
    }

    //===================================================================================================================================================================

    @GetMapping("/tutorials")
    public ResponseEntity<List<Labo>> getAllTutorials() {
        List<Labo> labos = new ArrayList<Labo>();


            laboRepository.findAll().forEach(labos::add);


        if (labos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(labos, HttpStatus.OK);
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Labo> getTutorialById(@PathVariable("id") long id) {
        Labo labo = laboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Labo with id = " + id));

        return new ResponseEntity<>(labo, HttpStatus.OK);
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Labo> createTutorial(@RequestBody Labo labo) {
        Labo _labo = laboRepository.save(new Labo(labo.getAcro_labo(), labo.getIntitule(), labo.getResponsable().getId()));
        return new ResponseEntity<>(_labo, HttpStatus.CREATED);
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Labo> updateTutorial(@PathVariable("id") long id, @RequestBody Labo tutorial) {
        Labo _labo = laboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Labo with id = " + id));



        return new ResponseEntity<>(laboRepository.save(_labo), HttpStatus.OK);
    }


    @PostMapping("a")
    public ResponseEntity<Labo> addTag(@PathVariable(value = "axeId") Long axeId, @RequestBody Labo laboRequest) {
        Labo labo = axeRepository.findById(axeId).map(axe -> {
            long laboId = laboRequest.getId();

            // labo is existed
//            if (laboId != 0L) {
//                Tag _tag = tagRepository.findById(tagId)
//                        .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
//                tutorial.addTag(_tag);
//                tutorialRepository.save(tutorial);
//                return _tag;
//            }

            // add and create new Tag
            axe.addLabos(laboRequest);
            return laboRepository.save(laboRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Axe with id = " + axeId));

        return new ResponseEntity<>(labo, HttpStatus.CREATED);
    }

}
