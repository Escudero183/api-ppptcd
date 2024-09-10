package gob.pe.devida.ppptcd.repository;

import java.util.List;

import javax.transaction.Transactional;

import gob.pe.devida.ppptcd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Sort;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u Where upper(u.login)=:login and u.status = true")
    public User findByLogin(String login);
    
    @Query("select u from User u Where u.status = true and upper(u.login) like :query")
	public List<User> findAll(String query, Sort sort);
    
    @Query("select u from User u Where u.status = true and (lower(u.login) like :query or lower(u.dni) like :query or lower(u.firstName) like :query or lower(u.lastName) like :query)")
	public Page<User> findAllParams(String query, Pageable pageable);
    
    @Query("select u from User u where u.status = true")
	public List<User> findAll(String query);
    
    @Query("select u from User u where u.status = true and idUser in(:users)")
	public List<User> findAll(List<Integer> users);

    @Transactional
    @Modifying
    @Query("update User u set u.password=:password Where u.idUser=:idUser")
    public void resetPassword(Integer idUser, String password);

}
