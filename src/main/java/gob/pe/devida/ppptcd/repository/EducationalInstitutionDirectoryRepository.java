package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.EducationalInstitutionDirectory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File created by Linygn Escudero$ on 12/11/2023$
 */
@Repository
public interface EducationalInstitutionDirectoryRepository extends JpaRepository<EducationalInstitutionDirectory, Integer> {

    @Query("select t from EducationalInstitutionDirectory t Where t.status = true and (:idEducationalInstitution = -1 or (:idEducationalInstitution != -1 and (t.idEducationalInstitution = :idEducationalInstitution))) and (lower(t.firstName) like :query or lower(t.lastNameOne) like :query or lower(t.lastNameTwo) like :query)")
    public Page<EducationalInstitutionDirectory> findAllParams(Integer idEducationalInstitution, String query, Pageable pageable);

    @Query("select t from EducationalInstitutionDirectory t Where t.status = true and (:idEducationalInstitution = -1 or (:idEducationalInstitution != -1 and (t.idEducationalInstitution = :idEducationalInstitution))) and (lower(t.firstName) like :query or lower(t.lastNameOne) like :query or lower(t.lastNameTwo) like :query)")
    public List<EducationalInstitutionDirectory> findAll(Integer idEducationalInstitution, String query);

    @Query("select t from EducationalInstitutionDirectory t Where t.status = true and (:idEducationalInstitution = -1 or (:idEducationalInstitution != -1 and (t.idEducationalInstitution = :idEducationalInstitution))) and (lower(t.firstName) like :query or lower(t.lastNameOne) like :query or lower(t.lastNameTwo) like :query)")
    public List<EducationalInstitutionDirectory> findAll(Integer idEducationalInstitution, String query, Sort sort);
}
