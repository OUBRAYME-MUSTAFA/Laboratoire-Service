package com.example.laboratoireservice.entities;

import com.example.laboratoireservice.MODEL.Chercheur;
import com.example.laboratoireservice.MODEL.Equipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Labo  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String acro_labo;
    private String intitule;
    private Long ResponsableId;
    @Transient
    private Chercheur responsable;

    @Transient
    private  Set<Equipe> equipe = new HashSet<Equipe>();

    @ElementCollection
    private List<Long> equipes;

    @ManyToMany(mappedBy = "labo_list")
    private List<Axe> axe_list = new ArrayList<Axe>();


    public Labo(Long id, String acro_labo, String intitule, Long responsableId, Chercheur responsable) {
        this.id = id;
        this.acro_labo = acro_labo;
        this.intitule = intitule;
        ResponsableId = responsableId;
        this.responsable = responsable;
    }

    public Labo(String acro_labo, String Intitule){
        this.acro_labo= acro_labo;
        this.intitule = Intitule;

    }

    public List<Long> getEquipe_list(){
        return this.equipes;
    }




}
