package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.EducationalInstitution;
import gob.pe.devida.ppptcd.model.EducationalInstitution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */

@Repository
public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Integer> {
    @Query("select t from EducationalInstitution t Where t.status = true and (lower(t.modularCode) like :query or lower(t.name) like :query)")
    public Page<EducationalInstitution> findAllParams(String query, Pageable pageable);

    @Query("select t from EducationalInstitution t Where t.status = true and (lower(t.modularCode) like :query or lower(t.name) like :query)")
    public List<EducationalInstitution> findAll(String query);

    @Query("select t from EducationalInstitution t Where t.status = true and (lower(t.modularCode) like :query or lower(t.name) like :query)")
    public List<EducationalInstitution> findAll(String query, Sort sort);
}
