package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * File created by Linygn Escudero$ on 20/12/23$
 */
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer> {
}
