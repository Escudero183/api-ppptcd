package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.Student;
import gob.pe.devida.ppptcd.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File created by Linygn Escudero$ on 22/11/2023$
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("select t from Student t Where t.status = true and (-1 in (:idEducationalInstitution) or (-1 not in (:idEducationalInstitution) and (t.educationalInstitution.idEducationalInstitution in (:idEducationalInstitution)))) and (:stateEvolution = '' or (:stateEvolution != '' and t.stateEvolution = :stateEvolution)) and (:sex = '' or (:sex != '' and t.sex = :sex)) and (lower(t.firstName) like :query or lower(t.lastNameOne) like :query or lower(t.lastNameTwo) like :query)")
    public Page<Student> findAllParams(List<Integer> idEducationalInstitution, String stateEvolution, String sex, String query, Pageable pageable);

    @Query("select t from Student t Where t.status = true and (:idEducationalInstitution = -1 or (:idEducationalInstitution != -1 and (t.educationalInstitution.idEducationalInstitution = :idEducationalInstitution))) and (:stateEvolution = '' or (:stateEvolution != '' and t.stateEvolution = :stateEvolution)) and (:sex = '' or (:sex != '' and t.sex = :sex)) and (lower(t.firstName) like :query or lower(t.lastNameOne) like :query or lower(t.lastNameTwo) like :query)")
    public List<Student> findAll(Integer idEducationalInstitution, String stateEvolution, String sex, String query);

    @Query("select t from Student t Where t.status = true and (:idEducationalInstitution = -1 or (:idEducationalInstitution != -1 and (t.educationalInstitution.idEducationalInstitution = :idEducationalInstitution))) and (:stateEvolution = '' or (:stateEvolution != '' and t.stateEvolution = :stateEvolution)) and (:sex = '' or (:sex != '' and t.sex = :sex)) and (lower(t.firstName) like :query or lower(t.lastNameOne) like :query or lower(t.lastNameTwo) like :query)")
    public List<Student> findAll(Integer idEducationalInstitution, String stateEvolution, String sex, String query, Sort sort);

    @Query("select count(t) from Student t where t.status = true")
    Integer countByStatusTrue();
}
