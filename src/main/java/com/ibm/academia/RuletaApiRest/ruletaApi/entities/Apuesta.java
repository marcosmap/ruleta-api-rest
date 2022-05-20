package com.ibm.academia.RuletaApiRest.ruletaApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibm.academia.RuletaApiRest.ruletaApi.enums.ColorApuesta;
import com.ibm.academia.RuletaApiRest.ruletaApi.enums.ResultadoApuesta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "apuestas", schema = "ruleta")
public class Apuesta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "color_apuesta")
    @Enumerated(EnumType.STRING)
    private ColorApuesta colorApuesta;

    @Column(name = "cantidad", nullable = false)
    private Double  cantidad;

    @Column(name = "numero_resultante")
    private Integer numeroResultante;

    @Column(name = "color_apuesta_resultante")
    @Enumerated(EnumType.STRING)
    private ColorApuesta colorApuestaResultante;

    @Column(name = "resultado_apuesta")
    @Enumerated(EnumType.STRING)
    private ResultadoApuesta resultadoApuesta;

    @Column(name = "hora_apuesta")
    private Date horaApuesta;

    @ManyToOne(optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleta_id", foreignKey = @ForeignKey(name = "FK_RULETA_ID"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "apuestas"})
    private Ruleta ruleta;

    public Apuesta(Integer numero, ColorApuesta colorApuesta, Double cantidad) {
        this.numero = numero;
        this.colorApuesta = colorApuesta;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Apuesta{" +
                "id=" + id +
                ", numero=" + numero +
                ", colorApuesta=" + colorApuesta +
                ", cantidad=" + cantidad +
                ", numeroResultante=" + numeroResultante +
                ", colorApuestaResultante=" + colorApuestaResultante +
                ", resultadoApuesta=" + resultadoApuesta +
                '}';
    }

    private static final long serialVersionUID = 5901868012725886205L;

}
