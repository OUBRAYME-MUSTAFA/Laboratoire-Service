package com.example.laboratoireservice.repository;

import com.example.laboratoireservice.entities.Axe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AxeRepository extends JpaRepository<Axe, Long> {
    Axe getAxeById(Long axeID);

    Axe findByName(String name);
    //Collection<Axe> findById(Long id);
}

