package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.Incidence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * File created by Linygn Escudero$ on 22/11/2023$
 */
@Repository
public interface IncidenceRepository extends JpaRepository<Incidence, Integer> {
    @Query("select t from Incidence t Where t.status = true and (:idStudent = -1 or (:idStudent != -1 and t.idStudent = :idStudent)) and (:registrationDate = '' or (:registrationDate != '' and date(t.registrationDate) = date(:registrationDate))) and (lower(t.description) like :query or lower(t.measuresTaken) like :query)")
    public Page<Incidence> findAllParams(Integer idStudent, String registrationDate, String query, Pageable pageable);

    @Query("select t from Incidence t Where t.status = true and (:idStudent = -1 or (:idStudent != -1 and t.idStudent = :idStudent)) and (:registrationDate = '' or (:registrationDate != '' and date(t.registrationDate) = date(:registrationDate))) and (lower(t.description) like :query or lower(t.measuresTaken) like :query)")
    public List<Incidence> findAll(Integer idStudent, String registrationDate, String query);

    @Query("select t from Incidence t Where t.status = true and (:idStudent = -1 or (:idStudent != -1 and t.idStudent = :idStudent)) and (:registrationDate = '' or (:registrationDate != '' and date(t.registrationDate) = date(:registrationDate))) and (lower(t.description) like :query or lower(t.measuresTaken) like :query)")
    public List<Incidence> findAll(Integer idStudent, String registrationDate, String query, Sort sort);
}
