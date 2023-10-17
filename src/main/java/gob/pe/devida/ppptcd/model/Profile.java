package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Data
@Entity
@Table(name = "profile", schema = "security")
public class Profile {

    @Id
    @SequenceGenerator(name = "profile_generator_seq", sequenceName = "security.profile_id_profile_seq", allocationSize = 1)
    @GeneratedValue(generator = "profile_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idProfile;

    private String name;
}
