package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.TypeInstitution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Repository
public interface TypeInstitutionRepository extends JpaRepository<TypeInstitution, Integer> {
    @Query("select t from TypeInstitution t Where t.status = true and (lower(t.description) like :query or lower(t.abbreviation) like :query)")
    public Page<TypeInstitution> findAllParams(String query, Pageable pageable);

    @Query("select t from TypeInstitution t Where t.status = true and (lower(t.description) like :query or lower(t.abbreviation) like :query)")
    public List<TypeInstitution> findAll(String query);

    @Query("select t from TypeInstitution t Where t.status = true and (lower(t.description) like :query or lower(t.abbreviation) like :query)")
    public List<TypeInstitution> findAll(String query, Sort sort);
}
