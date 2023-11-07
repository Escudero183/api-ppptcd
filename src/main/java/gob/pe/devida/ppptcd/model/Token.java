package gob.pe.devida.ppptcd.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(of = { "idToken" })
@Table(name="token", schema = "security")
public class Token {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "token_generator_seq", sequenceName = "security.token_id_token_seq", allocationSize = 1)
    @GeneratedValue(generator = "token_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idToken;
    private String hash;
    private String hostname;
    private String nombreEquipo;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
