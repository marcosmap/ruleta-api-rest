package com.ibm.academia.RuletaApiRest.ruletaApi.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ruletas", schema = "ruleta")
public class Ruleta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "estado_ruleta", nullable = false)
    private boolean estadoRuleta;

    @Column(name = "hora_apertura")
    private Date horaApertura;

    @Column(name = "hora_cierre")
    private Date horaCierre;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ruleta", cascade = CascadeType.ALL)
    //@OneToMany(mappedBy = "ruleta", fetch = FetchType.LAZY)
    private Set<Apuesta> apuestas;



    @Override
    public String toString() {
        return "Ruleta{" +
                "id=" + id +
                ", estadoRuleta=" + estadoRuleta +
                '}';
    }

    private static final long serialVersionUID = -7929516436469925796L;

}
