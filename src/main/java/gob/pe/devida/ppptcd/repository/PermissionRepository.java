package gob.pe.devida.ppptcd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gob.pe.devida.ppptcd.model.Permission;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
	
	@Query("select t from Permission t Where t.status = true")
    public Page<Permission> findAllParams(Pageable pageable);

    @Query("select t from Permission t Where t.status = true")
    public List<Permission> findAll();
    
    @Query("select t from Permission t Where t.status = true and t.idProfile = :idProfile")
    public List<Permission> findByProfile(Integer idProfile);
    
    @Query("select t from Permission t Where t.status = true and t.idProfile = :idProfile and t.idModule = :idModule")
    public List<Permission> findByProfileAndModule(Integer idProfile, Integer idModule);

    @Query("select t from Permission t Where t.status = true")
    public List<Permission> findAll(Sort sort);

}
