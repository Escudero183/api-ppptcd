package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.Profile;
import gob.pe.devida.ppptcd.model.Profile;
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
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    @Query("select t from Profile t Where t.status = true and lower(t.name) like :query")
    public Page<Profile> findAllParams(String query, Pageable pageable);

    @Query("select t from Profile t Where t.status = true and lower(t.name) like :query")
    public List<Profile> findAll(String query);

    @Query("select t from Profile t Where t.status = true and lower(t.name) like :query")
    public List<Profile> findAll(String query, Sort sort);
}
