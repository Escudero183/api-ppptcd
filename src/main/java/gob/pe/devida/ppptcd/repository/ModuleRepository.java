package gob.pe.devida.ppptcd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gob.pe.devida.ppptcd.model.Module;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
	
	@Query("select t from Module t Where t.status = true and lower(t.name) like :query")
    public Page<Module> findAllParams(String query, Pageable pageable);

    @Query("select t from Module t Where t.status = true and lower(t.name) like :query")
    public List<Module> findAll(String query);
    
    @Query("select t from Module t Where t.status = true and t.idModuleParent = :idModuleParent order by t.orderIndex asc")
    public List<Module> findModuleParent(Integer idModuleParent);

    @Query("select t from Module t Where t.status = true and lower(t.name) like :query")
    public List<Module> findAll(String query, Sort sort);

}
