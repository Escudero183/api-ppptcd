package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.EducationalInstitutionDirectory;
import gob.pe.devida.ppptcd.model.InstitutionDirectory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File created by Linygn Escudero$ on 16/11/2023$
 */
@Repository
public interface InstitutionDirectoryRepository extends JpaRepository<InstitutionDirectory, Integer> {

    @Query("select t from InstitutionDirectory t Where t.status = true and (:idInstitution = -1 or (:idInstitution != -1 and (t.idInstitution = :idInstitution))) and (lower(t.firstName) like :query or lower(t.lastNameOne) like :query or lower(t.lastNameTwo) like :query)")
    public Page<InstitutionDirectory> findAllParams(Integer idInstitution, String query, Pageable pageable);

    @Query("select t from InstitutionDirectory t Where t.status = true and (:idInstitution = -1 or (:idInstitution != -1 and (t.idInstitution = :idInstitution))) and (lower(t.firstName) like :query or lower(t.lastNameOne) like :query or lower(t.lastNameTwo) like :query)")
    public List<InstitutionDirectory> findAll(Integer idInstitution, String query);

    @Query("select t from InstitutionDirectory t Where t.status = true and (:idInstitution = -1 or (:idInstitution != -1 and (t.idInstitution = :idInstitution))) and (lower(t.firstName) like :query or lower(t.lastNameOne) like :query or lower(t.lastNameTwo) like :query)")
    public List<InstitutionDirectory> findAll(Integer idInstitution, String query, Sort sort);
}
