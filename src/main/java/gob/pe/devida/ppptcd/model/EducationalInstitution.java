package gob.pe.devida.ppptcd.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

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

    private boolean status;

    @Transient
    private IUbigeo ubigeoAll;

    @OneToMany(mappedBy = "idEducationalInstitution")
    @Where(clause = "status = true")
    private List<EducationalInstitutionDirectory> directorio;

    @OneToMany(mappedBy = "idEducationalInstitution")
    @Where(clause = "status = true")
    private List<RiskPlace> riskPlaces;

}
