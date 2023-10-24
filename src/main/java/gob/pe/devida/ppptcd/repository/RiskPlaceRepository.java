package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.RiskPlace;
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
public interface RiskPlaceRepository extends JpaRepository<RiskPlace, Integer> {
    @Query("select t from RiskPlace t Where t.status = true and (:registrationStatus = '' or (:registrationStatus != '' and (t.registrationStatus=:registrationStatus))) and (lower(t.name) like :query or lower(t.address) like :query)")
    public Page<RiskPlace> findAllParams(String query, String registrationStatus, Pageable pageable);

    @Query("select t from RiskPlace t Where t.status = true and (:registrationStatus = '' or (:registrationStatus != '' and (t.registrationStatus=:registrationStatus))) and (lower(t.name) like :query or lower(t.address) like :query)")
    public List<RiskPlace> findAll(String query, String registrationStatus);

    @Query("select t from RiskPlace t Where t.status = true and (:registrationStatus = '' or (:registrationStatus != '' and (t.registrationStatus=:registrationStatus))) and (lower(t.name) like :query or lower(t.address) like :query)")
    public List<RiskPlace> findAll(String query, String registrationStatus, Sort sort);
}
