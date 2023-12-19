package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * File created by Linygn Escudero$ on 22/11/2023$
 */
@Data
@Entity
@Table(name = "incidence", schema = "ppptcd")
public class Incidence {
    @Id
    @SequenceGenerator(name = "incidence_generator_seq", sequenceName = "ppptcd.incidence_id_incidence_seq", allocationSize = 1)
    @GeneratedValue(generator = "incidence_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idIncidence;

    private Integer idStudent;

    private Integer idUser;

    private Date registrationDate;

    private String description;

    private String titleEvidenceOne;

    private String evidenceOne;

    private String titleEvidenceTwo;

    private String evidenceTwo;

    private String type;

    private String measuresTaken;

    private boolean status;
}
