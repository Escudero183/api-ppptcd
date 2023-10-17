package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */

@Data
@Entity
@Table(name = "educational_institution", schema = "setting")
public class EducationalInstitution {

    @Id
    @SequenceGenerator(name = "educational_institution_generator_seq", sequenceName = "setting.educational_institution_id_educational_institution_seq", allocationSize = 1)
    @GeneratedValue(generator = "educational_institution_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idEducationalInstitution;

    private String modularCode;

    private String name;

    private String levelModality;

    private String dependency;

    private String address;

    private String ubigeo;

    private String latitude;

    private String longitude;

    private boolean status;

}
