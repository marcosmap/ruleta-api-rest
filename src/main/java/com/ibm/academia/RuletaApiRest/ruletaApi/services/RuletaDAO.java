package com.ibm.academia.RuletaApiRest.ruletaApi.services;

import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Apuesta;
import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Ruleta;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface RuletaDAO {
    public Ruleta creaRuleta(Ruleta ruleta);
    public ResponseEntity<?> abreRuleta(Integer id);
    public Iterable<Ruleta> listaRuletas();
    public List<Apuesta> cierraApuesta(Integer ruletaId);
    public Optional<Ruleta> buscaRuletaPorId(Integer ruletaId);
}
