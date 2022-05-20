package com.ibm.academia.RuletaApiRest.ruletaApi.controllers;

import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Apuesta;
import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Ruleta;
import com.ibm.academia.RuletaApiRest.ruletaApi.exceptions.handler.BadRequestException;
import com.ibm.academia.RuletaApiRest.ruletaApi.services.ApuestaDAO;
import com.ibm.academia.RuletaApiRest.ruletaApi.services.RuletaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ruleta")
public class RuletaController {

    @Autowired
    private RuletaDAO ruletaDAO;
    @Autowired
    private ApuestaDAO apuestaDAO;

    @GetMapping
    public ResponseEntity<?> crearRuleta () {
        return new ResponseEntity<Ruleta>(ruletaDAO.creaRuleta(new Ruleta()), HttpStatus.CREATED);
    }

    @GetMapping("/abre/{ruletaId}")
    public ResponseEntity<?> abreRuleta (@PathVariable Integer ruletaId) {
        return new ResponseEntity<String>(ruletaDAO.abreRuleta(ruletaId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/apuesta/{ruletaId}")
    public ResponseEntity<?> creaApuesta (@RequestBody Apuesta apuesta, @PathVariable Integer ruletaId) {
        return new ResponseEntity<Apuesta>(apuestaDAO.creaApuesta(apuesta, ruletaId), HttpStatus.CREATED);
    }

    @GetMapping("/lista/resultados/apuestas/{ruletaId}")
    public ResponseEntity<?> cierraApuesta (@PathVariable Integer ruletaId) {
        List<Apuesta> apuestas = (List<Apuesta>) ruletaDAO.cierraApuesta(ruletaId);
        if (apuestas.isEmpty())
            throw new BadRequestException(String.format("No existen apuestas en la ruleta con ID: %d", ruletaId));
        return new ResponseEntity<List<Apuesta>>(apuestas, HttpStatus.OK);
    }

    @GetMapping("/lista/ruletas")
    public ResponseEntity<?> listaRuletas() {
        return new ResponseEntity<List<Ruleta>>((List<Ruleta>) ruletaDAO.listaRuletas(), HttpStatus.OK);
    }

}
