package com.ibm.academia.RuletaApiRest.ruletaApi.repositories;

import com.ibm.academia.RuletaApiRest.ruletaApi.entities.Ruleta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuletaRepository extends CrudRepository<Ruleta, Integer> {
}
