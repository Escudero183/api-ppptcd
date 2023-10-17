package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.EducationalInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */

@Repository
public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Integer> {
}
