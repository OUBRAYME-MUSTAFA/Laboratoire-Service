package com.example.laboratoireservice.entities;

import com.example.laboratoireservice.MODEL.Chercheur;
import com.example.laboratoireservice.MODEL.Equipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Labo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String acro_labo;
    private String Intitule;
    @Transient
    private Chercheur responsable;
    @Transient
    @OneToMany(mappedBy = "equipe_list")
    private List<Equipe> equipe;
    @ManyToMany(mappedBy = "labo_list")
    private List<Axe> axe_list = new ArrayList<Axe>();


    public Labo(String acro_labo,String Intitule,Chercheur  responsable){
        this.acro_labo= acro_labo;
        this.Intitule= Intitule;
        this.responsable= responsable;
    }
    public Labo(String acro_labo,String Intitule){
        this.acro_labo= acro_labo;
        this.Intitule= Intitule;

    }

    public List<Equipe> getEquipe_list(){
        return this.equipe;
    }


}
