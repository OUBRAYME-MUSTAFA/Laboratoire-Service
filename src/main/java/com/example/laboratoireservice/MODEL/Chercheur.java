package com.example.laboratoireservice.MODEL;

import com.example.laboratoireservice.entities.Labo;
import com.example.laboratoireservice.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Chercheur {
    @Id
    private Long id ;
    private String nom;
    private String prenom ;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "member")
    @JsonIgnore
    private List<Labo> labos;

    public void addLabos(Labo labo) {
        this.labos.add(labo);
        labo.getMember().add(this);
    }


}
