package com.ibm.academia.RuletaApiRest.ruletaApi.repositories;

import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Apuesta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApuestaRepository extends CrudRepository<Apuesta, Integer> {
}
