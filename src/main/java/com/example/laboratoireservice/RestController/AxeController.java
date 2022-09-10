package com.example.laboratoireservice.RestController;

import com.example.laboratoireservice.entities.Axe;
import com.example.laboratoireservice.repository.AxeRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class AxeController {

    private AxeRepository axeRepository;

    public AxeController(AxeRepository axeRepository) {
        this.axeRepository = axeRepository;
    }

    @PostMapping("addAxe")
    public Axe addLabo(@RequestBody Axe axe ){

        return axeRepository.save(axe);

    }

    @PutMapping("updateAxe/{id}")
    public Axe addAxe(@RequestBody Axe axe, @PathVariable long id) {
        Axe newAxe = axeRepository.findById(id).get();
        newAxe.setId(axe.getId());
        newAxe.setName(axe.getName());
        return axeRepository.save(newAxe);
    }

}
