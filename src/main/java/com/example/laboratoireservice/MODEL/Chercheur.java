package com.example.laboratoireservice.MODEL;

import com.example.laboratoireservice.entities.Labo;
import com.example.laboratoireservice.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chercheur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    private String role;
    @ManyToMany
    private List<Labo> labo;

}
