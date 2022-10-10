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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "laboratoire_axes",
            joinColumns = { @JoinColumn(name = "labo_id") },
            inverseJoinColumns = { @JoinColumn(name = "axe_id") })
    private List<Axe> axes = new ArrayList<>();

    public List<Axe> getAxes() {
        return axes;
    }

    @ManyToMany(mappedBy = "labo")
    private Set<Chercheur> Member = new HashSet<Chercheur>();


    public Labo( Long id , String acro_labo, String intitule, Long responsableId) {
        this.acro_labo = acro_labo;
        this.intitule = intitule;
        ResponsableId = responsableId;
        this.id=id;
    }

    public Labo(String acro_labo, String intitule, Long responsableId) {
        this.acro_labo = acro_labo;
        this.intitule = intitule;
        ResponsableId = responsableId;
    }

    public Labo(String acro_labo, String Intitule){
        this.acro_labo= acro_labo;
        this.intitule = Intitule;

    }
    public void addAxe(Axe axe) {
        this.axes.add(axe);
    }

    public void removeAxe(long axeId) {
        Axe axe = this.axes.stream().filter(t -> t.getId() == axeId).findFirst().orElse(null);
        if (axe != null) {
            this.axes.remove(axe);
            axe.getLabos().remove(this);
        }
    }
    public List<Long> getEquipe_list(){
        return this.equipes;
    }

//    public void  addAxe_Labo(List axes){
//        axes.forEach(pi->{
//            this.axe_list.add((Axe) pi);
//        });
//    }




}
