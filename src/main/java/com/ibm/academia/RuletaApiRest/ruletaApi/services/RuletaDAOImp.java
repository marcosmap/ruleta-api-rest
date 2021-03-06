package com.ibm.academia.RuletaApiRest.ruletaApi.services;

import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Apuesta;
import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Ruleta;
import com.ibm.academia.RuletaApiRest.ruletaApi.exceptions.handler.BadRequestException;
import com.ibm.academia.RuletaApiRest.ruletaApi.exceptions.handler.NotFoundException;
import com.ibm.academia.RuletaApiRest.ruletaApi.repositories.RuletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RuletaDAOImp implements RuletaDAO{
    @Autowired
    private RuletaRepository ruletaRepository;

    @Override
    @Transactional
    public Ruleta creaRuleta(Ruleta ruleta) {
        return ruletaRepository.save(ruleta);
    }

    @Override
    @Transactional
    public ResponseEntity<?> abreRuleta(Integer id) {
        Optional<Ruleta> ruleta = ruletaRepository.findById(id);
        if (!ruleta.isPresent())
            throw new NotFoundException(String.format("La ruleta con ID: %d no existe", id));
        if (!ruleta.get().isEstadoRuleta()) {
            ruleta.get().setEstadoRuleta(true);
            ruleta.get().setHoraApertura(new Date());
            ruletaRepository.save(ruleta.get());
            Map<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("estado", String.format("La ruleta con ID: %d ha sido abierta", id));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
        }
        else
            throw new BadRequestException(String.format("La ruleta con ID: %d esta abierta", id));
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Ruleta> listaRuletas() {
        List<Ruleta> listaRuletas = (List<Ruleta>) ruletaRepository.findAll();

        if (listaRuletas.isEmpty()) {
            throw new NotFoundException("No se encontraron ruletas");
        }
        return listaRuletas;
    }

    @Override
    @Transactional
    public List<Apuesta> cierraApuesta(Integer ruletaId) {
        Optional<Ruleta> ruleta = ruletaRepository.findById(ruletaId);
        List<Apuesta> apuestasByRuletaId = new ArrayList<>();

        if (!ruleta.isPresent())
            throw new BadRequestException(String.format("La ruleta con ID: %d no existe", ruletaId));
        else {
            if (ruleta.get().isEstadoRuleta()) {
                ruleta.get().setHoraCierre(new Date());
                ruleta.get().setEstadoRuleta(false);
                Set<Apuesta> apuestas =  ruleta.get().getApuestas();
                for(Apuesta apuesta: apuestas) {
                    if ( apuesta.getRuleta().getId().equals(ruletaId) && (apuesta.getRuleta().getHoraApertura().before(apuesta.getHoraApuesta())) )
                        apuestasByRuletaId.add(apuesta);
                }
                ruletaRepository.save(ruleta.get());
                return apuestasByRuletaId;
            }
            else
                throw new BadRequestException(String.format("Ruleta con ID: %d esta cerrada", ruletaId));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ruleta> buscaRuletaPorId(Integer ruletaId) {
        Optional<Ruleta> ruleta = ruletaRepository.findById(ruletaId);
        if (ruleta.isPresent())
            return ruleta;
        else
            throw new BadRequestException(String.format("Ruleta con Id: %d no existe", ruletaId));
    }

}
