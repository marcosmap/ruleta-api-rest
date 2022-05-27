package com.ibm.academia.RuletaApiRest.ruletaApi.controllers;

import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Apuesta;
import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Ruleta;
import com.ibm.academia.RuletaApiRest.ruletaApi.exceptions.handler.BadRequestException;
import com.ibm.academia.RuletaApiRest.ruletaApi.services.ApuestaDAO;
import com.ibm.academia.RuletaApiRest.ruletaApi.services.RuletaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ruleta")
public class RuletaController {

    @Autowired
    private RuletaDAO ruletaDAO;
    @Autowired
    private ApuestaDAO apuestaDAO;

    /**
     * Mètodo para la creaciòn de objetos Ruleta
     * @return Este metodo retorna un objeto ruleta con sus atributos
     * @author MMAP 20/05/2022
     */
    @GetMapping
    public ResponseEntity<?> crearRuleta () {
        return new ResponseEntity<Ruleta>(ruletaDAO.creaRuleta(new Ruleta()), HttpStatus.CREATED);
    }

    /**
     * Este metodo funciona para establecer el estado de una ruleta como 'abierta' y este disponible para hacer apuestas
     * @param ruletaId -> este sera el parametro para abrir una ruleta en especifico
     * @return Retorna el resultado de la peticion 'Abierta' o 'Fallida'
     * @BadRequestException retorna un error indicando si la ruleta esta abierta
     * @author MMAP 20/05/2022
     */
    @GetMapping("/abre/{ruletaId}")
    public ResponseEntity<?> abreRuleta (@PathVariable Integer ruletaId) {
        return ruletaDAO.abreRuleta(ruletaId);
    }

    /**
     * Este metodo permite realizar una apuesta en una ruleta en especifico y abierta
     * @param apuesta se necesita un objeto Apuesta con los datos que llevara la apuesta como los son cantidad de dinero a apostar, numero o color
     * @param ruletaId el id de la ruleta en donde se va a apostar
     * @return Retorna el resultado de la apuesta en el objeto Apuesta
     * @author MMAP 20/05/2022
     */
    @PostMapping("/apuesta/{ruletaId}")
    public ResponseEntity<?> creaApuesta (@Valid @RequestBody Apuesta apuesta, @PathVariable Integer ruletaId, BindingResult result) {
        Map<String, Object> validaciones = new HashMap<String, Object>();
        if (result.hasErrors()) {
            List<String> listaErrores = result.getFieldErrors()
                    .stream()
                    .map(errores -> "Campo '" + errores.getField() + "' " + errores.getDefaultMessage())
                    .collect(Collectors.toList());
            validaciones.put("Lista errores", listaErrores);
            return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Apuesta>(apuestaDAO.creaApuesta(apuesta, ruletaId), HttpStatus.CREATED);
    }

    /**
     * Cierra una ruleta abierta
     * @param ruletaId para buscar la ruleta a cerrar
     * @return Retornara la lista de apuestas desde su apertura
     * @author MMAP 20/05/2022
     */
    @GetMapping("/lista/resultados/apuestas/{ruletaId}")
    public ResponseEntity<?> cierraApuesta (@PathVariable Integer ruletaId) {
        List<Apuesta> apuestas = (List<Apuesta>) ruletaDAO.cierraApuesta(ruletaId);
        if (apuestas.isEmpty())
            throw new BadRequestException(String.format("No existen apuestas en la ruleta con ID: %d", ruletaId));
        return new ResponseEntity<List<Apuesta>>(apuestas, HttpStatus.OK);
    }

    /**
     * Lista todas las ruletas creadas
     * @return Retorna la lista de todas las rueltas con sus atributos
     * @author MMAP 20/05/2022
     */
    @GetMapping("/lista/ruletas")
    public ResponseEntity<?> listaRuletas() {
        return new ResponseEntity<List<Ruleta>>((List<Ruleta>) ruletaDAO.listaRuletas(), HttpStatus.OK);
    }

}
