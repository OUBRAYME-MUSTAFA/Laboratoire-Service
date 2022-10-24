package com.example.laboratoireservice.RestController;

import com.example.laboratoireservice.entities.Axe;
import com.example.laboratoireservice.entities.Labo;
import com.example.laboratoireservice.repository.AxeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("http://localhost:4200/")
public class AxeController {

    private AxeRepository axeRepository;

    public AxeController(AxeRepository axeRepository) {
        this.axeRepository = axeRepository;
    }

    @GetMapping(path = "/axes")
    public List<Axe> listAxes()
    {

        List<Axe> list = axeRepository.findAll();
        list.forEach(pi->{
            pi.setLabos(null);
        });
        return list;
    }
    @GetMapping(path = "/axes/{id}")
    public Axe listAxes(@PathVariable(name = "id") Long  id)
    {
        Axe newAxe =  axeRepository.findById(id).get();
        newAxe.setLabos(null);
        return newAxe;
    }
    @GetMapping(path = "/getAxeByName/{name}")
    public Axe findByName(@PathVariable(name = "name") String  name)
    {
        return axeRepository.findByName(name);
    }
    @PostMapping("addAxe")
    public Axe addLabo(@RequestBody Axe axe ){

        return axeRepository.save(axe);

    }

    @PutMapping("updateAxe")
    public Axe addAxe(@RequestBody Axe axe) {

        return axeRepository.save(axe);
    }
    @DeleteMapping(path = "axe/{code}")
    public void deleteAxe(@PathVariable (name = "code") long code ) {

        axeRepository.deleteById(code);
    }

}
