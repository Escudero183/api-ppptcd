package gob.pe.devida.ppptcd.repository;

import gob.pe.devida.ppptcd.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
