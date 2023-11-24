package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.Treatment;
import gob.pe.devida.ppptcd.model.Treatment;
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
public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {
    @Query("select t from Treatment t Where t.status = true and (:idStudent = -1 or (:idStudent != -1 and t.idStudent = :idStudent)) and (:idSpecialist = -1 or (:idSpecialist != -1 and t.idSpecialist = :idSpecialist)) and (:registrationDate = '' or (:registrationDate != '' and date(t.registrationDate) = date(:registrationDate))) and (lower(t.admissionReason) like :query or lower(t.diagnosis) like :query)")
    public Page<Treatment> findAllParams(Integer idStudent, Integer idSpecialist, String registrationDate, String query, Pageable pageable);

    @Query("select t from Treatment t Where t.status = true and (:idStudent = -1 or (:idStudent != -1 and t.idStudent = :idStudent)) and (:idSpecialist = -1 or (:idSpecialist != -1 and t.idSpecialist = :idSpecialist)) and (:registrationDate = '' or (:registrationDate != '' and date(t.registrationDate) = date(:registrationDate))) and (lower(t.admissionReason) like :query or lower(t.diagnosis) like :query)")
    public List<Treatment> findAll(Integer idStudent, Integer idSpecialist, String registrationDate, String query);

    @Query("select t from Treatment t Where t.status = true and (:idStudent = -1 or (:idStudent != -1 and t.idStudent = :idStudent)) and (:idSpecialist = -1 or (:idSpecialist != -1 and t.idSpecialist = :idSpecialist)) and (:registrationDate = '' or (:registrationDate != '' and date(t.registrationDate) = date(:registrationDate))) and (lower(t.admissionReason) like :query or lower(t.diagnosis) like :query)")
    public List<Treatment> findAll(Integer idStudent, Integer idSpecialist, String registrationDate, String query, Sort sort);
}
