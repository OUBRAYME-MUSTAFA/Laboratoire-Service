package com.example.laboratoireservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Axe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany
    private List<Labo> labo_list = new ArrayList<Labo>();

    public void setAxeName(String name) {
        this.name = name;
    }

    public void addEquipe( Labo labo){
        labo_list.add(labo);
    }
    public Axe(String name){
        this.name= name;
    }


}