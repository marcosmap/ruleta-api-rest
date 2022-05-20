package com.ibm.academia.RuletaApiRest.ruletaApi.services;

import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Apuesta;
import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Ruleta;

import java.util.List;

public interface ApuestaDAO {
    public Apuesta creaApuesta(Apuesta apuesta, Integer ruletaId);
}
