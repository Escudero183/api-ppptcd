package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("select t from Token t where t.user.idUser=:idUser and t.hash=:token and t.nombreEquipo=:userAgent")
    Token findByHashAndUser(String token, Integer idUser, String userAgent);

    @Modifying
    @Query("delete from Token t where t.user.idUser=:idUser")
    void deleteByUser(Integer idUser);
}
