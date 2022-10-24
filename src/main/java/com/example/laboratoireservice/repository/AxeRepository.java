package com.example.laboratoireservice.repository;

import com.example.laboratoireservice.entities.Axe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AxeRepository extends JpaRepository<Axe, Long> {
    Axe getAxeById(Long axeID);

    Axe findByName(String name);
    //Collection<Axe> findById(Long id);
    List<Axe> findAxessByLabosId(Long laboId);

    List<Axe> searchByName(String name);
}

