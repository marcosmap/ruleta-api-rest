package com.ibm.academia.RuletaApiRest.ruletaApi.services;

import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Apuesta;
import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Ruleta;
import com.ibm.academia.RuletaApiRest.ruletaApi.enums.ColorApuesta;
import com.ibm.academia.RuletaApiRest.ruletaApi.enums.ResultadoApuesta;
import com.ibm.academia.RuletaApiRest.ruletaApi.exceptions.handler.BadRequestException;
import com.ibm.academia.RuletaApiRest.ruletaApi.repositories.ApuestaRepository;
import com.ibm.academia.RuletaApiRest.ruletaApi.repositories.RuletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class ApuestaDAOImp implements ApuestaDAO{
    @Autowired
    ApuestaRepository apuestaRepository;
    @Autowired
    RuletaRepository ruletaRepository;

    @Override
    @Transactional
    public Apuesta creaApuesta(Apuesta apuesta, Integer ruletaId) {
        Optional<Ruleta> ruleta = ruletaRepository.findById(ruletaId);
        if (ruleta.isPresent()) {

            if (ruleta.get().isEstadoRuleta()) {
                if (apuesta.getColorApuesta() == null && apuesta.getNumero() == null) {
                    throw new BadRequestException("Se debe dar un numero o un color para apostar");
                }

                if (apuesta.getColorApuesta() != null && apuesta.getNumero() != null) {
                    throw new BadRequestException("Solo se puede apostar a un numero o a un color pero no a ambos");
                }

                if (apuesta.getColorApuesta() == null && apuesta.getNumero() != null) {
                    if (apuesta.getNumero() < 0 || apuesta.getNumero() > 36)
                        throw new BadRequestException("Solo se aceptan numeros del 0-36");
                    else {
                        int numRandom = (int) (Math.random() * 36);
                        apuesta.setNumeroResultante(numRandom);
                        if (apuesta.getNumero() == numRandom)
                            apuesta.setResultadoApuesta(ResultadoApuesta.GANADA);
                        else
                            apuesta.setResultadoApuesta(ResultadoApuesta.PERDIDA);
                    }
                }

                if (apuesta.getColorApuesta() != null && apuesta.getNumero() == null) {
                    if (apuesta.getColorApuesta().equals("NEGRO") || apuesta.getColorApuesta().equals("ROJO")) {
                        int numRandom = (int) (Math.random() * 2);
                        System.out.println(numRandom);
                        if (apuesta.getColorApuesta().equals("NEGRO")) {
                            if (numRandom == 0) {
                                apuesta.setColorApuestaResultante(ColorApuesta.NEGRO);
                                apuesta.setResultadoApuesta(ResultadoApuesta.GANADA);
                            }
                            else {
                                apuesta.setColorApuestaResultante(ColorApuesta.ROJO);
                                apuesta.setResultadoApuesta(ResultadoApuesta.PERDIDA);
                            }
                        }
                        else {
                            if (numRandom == 1) {
                                apuesta.setColorApuestaResultante(ColorApuesta.ROJO);
                                apuesta.setResultadoApuesta(ResultadoApuesta.GANADA);
                            }
                            else {
                                apuesta.setColorApuestaResultante(ColorApuesta.NEGRO);
                                apuesta.setResultadoApuesta(ResultadoApuesta.PERDIDA);
                            }
                        }
                    }
                    else
                        throw new BadRequestException("Colores validos: NEGRO - ROJO");
                }

                if (apuesta.getCantidad() == null)
                    throw new BadRequestException("No se permiten valores nulos, se debe ingresar una cantidad de dinero valida");
                else if (apuesta.getCantidad() <= 0 || apuesta.getCantidad() > 10000)
                    throw new BadRequestException("Solo se aceptan apuestas de 1 a 10000 dolares");

                apuesta.setHoraApuesta(new Date());
                apuesta.setRuleta(ruleta.get());
                return apuestaRepository.save(apuesta);
            }
            else
                throw new BadRequestException("La ruleta no esta abierta");

        }
        else
            throw new BadRequestException("La ruleta ingresada no existe");
    }

}
