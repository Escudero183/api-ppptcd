package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.TypeRiskPlace;
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
public interface TypeRiskPlaceRepository extends JpaRepository<TypeRiskPlace, Integer> {
    @Query("select t from TypeRiskPlace t Where t.status = true and (lower(t.description) like :query or lower(t.name) like :query)")
    public Page<TypeRiskPlace> findAllParams(String query, Pageable pageable);

    @Query("select t from TypeRiskPlace t Where t.status = true and (lower(t.description) like :query or lower(t.name) like :query)")
    public List<TypeRiskPlace> findAll(String query);

    @Query("select t from TypeRiskPlace t Where t.status = true and (lower(t.description) like :query or lower(t.name) like :query)")
    public List<TypeRiskPlace> findAll(String query, Sort sort);
}
