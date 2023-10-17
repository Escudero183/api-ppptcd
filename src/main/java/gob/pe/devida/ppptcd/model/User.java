package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Data
@Entity
@Table(name = "user", schema = "security")
public class User {

    @Id
    @SequenceGenerator(name = "user_generator_seq", sequenceName = "security.user_id_user_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idUser;

    private Integer idInstitution;

    private Integer idProfile;

    private String dni;

    private String firstName;

    private String lastName;

    private String login;

    private String password;

    private String email;

    private String display;

    private boolean status;
}
