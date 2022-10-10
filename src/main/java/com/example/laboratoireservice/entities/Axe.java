package com.example.laboratoireservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Axe  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "axes")
    @JsonIgnore
    private Set<Labo> labos = new HashSet<>();

    public void setAxeName(String name) {
        this.name = name;
    }

    public Axe(String name){
        this.name= name;
    }


    public Set<Labo> getLabos() {
        return  this.labos;
    }

    public void setLabos(Set<Labo> labos) {
        this.labos = labos;
    }
    public void addLabos(Labo labo) {
        this.labos.add(labo);
        labo.getAxes().add(this);
    }
}