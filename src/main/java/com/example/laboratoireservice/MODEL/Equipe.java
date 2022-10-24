package com.example.laboratoireservice.MODEL;

import com.example.laboratoireservice.entities.Axe;
import com.example.laboratoireservice.entities.Labo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Equipe {
    @Id
    private Long id;
    private String acro_equipe;
    private String intitule;
    private Chercheur responsable;
    @Transient
    private List<Axe> axes = new ArrayList<Axe>();
    private List<Chercheur> member = new ArrayList<>();
    @ManyToOne
    private Labo labo;
    private long laboID;

    public void addAxe( Axe axe){
        axes.add(axe);
    }

    public Equipe(String acro_equipe,String Intitule,Chercheur  responsable){
        this.acro_equipe= acro_equipe;
        this.intitule= Intitule;
        this.responsable= responsable;
    }

}
