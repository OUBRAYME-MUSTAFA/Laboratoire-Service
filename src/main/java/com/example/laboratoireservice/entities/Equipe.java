package com.example.laboratoireservice.entities;

import com.example.laboratoireservice.entities.Axe;
import com.example.laboratoireservice.entities.Labo;
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
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String acro_equipe;
    private String Intitule;
    private String responsable;
    @ManyToMany
    private List<Axe> axe_list = new ArrayList<Axe>();
    @ManyToOne
    private Labo labo;

    public void addAxe( Axe axe){
        axe_list.add(axe);
    }

    public Equipe(String acro_equipe,String Intitule,String  responsable){
        this.acro_equipe= acro_equipe;
        this.Intitule= Intitule;
        this.responsable= responsable;
    }

}
