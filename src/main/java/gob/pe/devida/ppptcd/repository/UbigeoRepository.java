package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.IUbigeo;
import gob.pe.devida.ppptcd.model.Ubigeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * File created by Linygn Escudero$ on 02/11/2023$
 */

@Repository
public interface UbigeoRepository extends JpaRepository<Ubigeo, String> {
    @Query("select u from Ubigeo u Where u.idUbigeo like '%0000'")
    public List<Ubigeo> findDptos();
    
    @Query(value = "SELECT "
            + "u.id_ubigeo as idUbigeo, "
            + "(select udpto.description From setting.ubigeo as udpto Where udpto.id_ubigeo = substring(:codUbigeo,1,2) || '0000') as dpto, "
            + "(select udpto.description From setting.ubigeo as udpto Where udpto.id_ubigeo = substring(:codUbigeo,1,4) || '00') as prov, "
            + "u.description as dist "
            + "FROM setting.ubigeo as u WHERE u.id_ubigeo = :codUbigeo",
            nativeQuery = true)
    public IUbigeo getUbigeoFull(String codUbigeo);

    @Query("select u from Ubigeo u Where u.idUbigeo like :codUbigeo || '%00' and u.idUbigeo != :codUbigeo || '0000' ")
    public List<Ubigeo> findProvs(String codUbigeo);

    @Query("select u from Ubigeo u Where u.idUbigeo like :codUbigeo || '%' and u.idUbigeo != :codUbigeo || '00' ")
    public List<Ubigeo> findDists(String codUbigeo);

    @Query("select u from Ubigeo u Where u.idUbigeo=:codUbigeo ")
    public Optional<Ubigeo> findById(String codUbigeo);
}
