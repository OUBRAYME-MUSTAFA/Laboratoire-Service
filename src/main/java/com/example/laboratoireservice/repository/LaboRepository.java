package com.example.laboratoireservice.repository;

import com.example.laboratoireservice.entities.Labo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface LaboRepository extends JpaRepository<Labo, Long> {
    List<Labo> findLabosByAxesId(Long axeId);
}
