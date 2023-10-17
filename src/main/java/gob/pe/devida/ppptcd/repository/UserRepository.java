package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u Where upper(u.login)=:login")
    public User findByLogin(String login);

    @Modifying
    @Query("update User u set u.password=:password Where u.idUser=:idUser")
    public void resetPassword(Integer idUser, String password);

}
