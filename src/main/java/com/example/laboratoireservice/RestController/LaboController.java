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
@CrossOrigin("http://localhost:4200/")
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
            Chercheur chercheur = chercheurRestClient.getChercheurById(labo.getResponsableId());
            labo.setResponsable(chercheur);
            labo.getAxes().forEach(pi->{
                Axe axe = axeRepository.getAxeById(pi.getId());
                pi.setAxeName(axe.getName());
                pi.setLabos(null);
            });
            labo.getMember().forEach(ch->{
                Chercheur chercheur1 = chercheurRestClient.getChercheurByName(ch.getName());
                ch.setChercheurName(chercheur1.getName());
                ch.setLabos(null);
            });

//            labo.getEquipe_list().forEach(pi->{
//                Equipe equipe = equipeRestClient.getEquipeById(pi);
//                labo.getEquipe().add(equipe);
//            });

        });
        return list__;
    }



@PostMapping("addLabo")
public ResponseEntity<Labo> addLabo(@RequestBody Labo labo){
    Chercheur chercheur = chercheurRestClient.getChercheurByName(labo.getResponsable().getName());
    Labo labo1 =new Labo(labo.getId(),labo.getAcro_labo(), labo.getIntitule(),chercheur.getId());
    laboRepository.save(labo1);
    labo.getAxes().forEach(pi->{
        addAxe(axeRepository.findById(pi.getId()).get(),labo1.getId());

    });
    labo.getMember().forEach(member->{
        Chercheur newChercheur =chercheurRestClient.getChercheurById(member.getId());
        System.out.println("********************** id = "+newChercheur.getId()+" // name = "+newChercheur.getName());
        addMember(newChercheur,labo1.getId());
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
        Labo labo =  laboRepository.findById(id).get();
        labo.addMember(chercheur);
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
//
//    @GetMapping("/tutorials")
//    public ResponseEntity<List<Labo>> getAllTutorials() {
//        List<Labo> labos = new ArrayList<Labo>();
//
//
//            laboRepository.findAll().forEach(labos::add);
//
//
//        if (labos.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return new ResponseEntity<>(labos, HttpStatus.OK);
//    }
//
//    @GetMapping("/tutorials/{id}")
//    public ResponseEntity<Labo> getTutorialById(@PathVariable("id") long id) {
//        Labo labo = laboRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Not found Labo with id = " + id));
//
//        return new ResponseEntity<>(labo, HttpStatus.OK);
//    }
//
//    @PostMapping("/tutorials")
//    public ResponseEntity<Labo> createTutorial(@RequestBody Labo labo) {
//        Labo _labo = laboRepository.save(new Labo(labo.getAcro_labo(), labo.getIntitule(), labo.getResponsable().getId()));
//        return new ResponseEntity<>(_labo, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/tutorials/{id}")
//    public ResponseEntity<Labo> updateTutorial(@PathVariable("id") long id, @RequestBody Labo tutorial) {
//        Labo _labo = laboRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Not found Labo with id = " + id));
//
//
//
//        return new ResponseEntity<>(laboRepository.save(_labo), HttpStatus.OK);
//    }
//
//
//    @PostMapping("a")
//    public ResponseEntity<Labo> addTag(@PathVariable(value = "axeId") Long axeId, @RequestBody Labo laboRequest) {
//        Labo labo = axeRepository.findById(axeId).map(axe -> {
//            long laboId = laboRequest.getId();
//
//            // labo is existed
////            if (laboId != 0L) {
////                Tag _tag = tagRepository.findById(tagId)
////                        .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
////                tutorial.addTag(_tag);
////                tutorialRepository.save(tutorial);
////                return _tag;
////            }
//
//            // add and create new Tag
//            axe.addLabos(laboRequest);
//            return laboRepository.save(laboRequest);
//        }).orElseThrow(() -> new ResourceNotFoundException("Not found Axe with id = " + axeId));
//
//        return new ResponseEntity<>(labo, HttpStatus.CREATED);
//    }

}
