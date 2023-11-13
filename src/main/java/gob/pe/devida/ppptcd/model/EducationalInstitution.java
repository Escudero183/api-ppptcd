package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */

@Data
@Entity
@Table(name = "educational_institution", schema = "ppptcd")
public class EducationalInstitution {

    @Id
    @SequenceGenerator(name = "educational_institution_generator_seq", sequenceName = "ppptcd.educational_institution_id_educational_institution_seq", allocationSize = 1)
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

    private String dataEscale;

    private String website;

    private String email;

    private String phone;

    private String additionalData;

    private String directorPhone;

    private String subDirector;

    private String subDirectorPhone;

    private String tutoringCoordinator;

    private String tutoringCoordinatorPhone;

    private boolean status;

}
