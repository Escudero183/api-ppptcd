package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 18/10/2023$
 */

@Data
@Entity
@Table(name = "risk_place", schema = "ppptcd")
public class RiskPlace {

    @Id
    @SequenceGenerator(name = "risk_place_generator_seq", sequenceName = "ppptcd.risk_place_id_risk_place_seq", allocationSize = 1)
    @GeneratedValue(generator = "risk_place_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idRiskPlace;

    private String name;

    private String registrationName;

    private String description;

    private String address;

    private String ubigeo;

    private String latitude;

    private String longitude;

    private String registrationStatus;

    private boolean status;

    private Integer idEducationalInstitution;

    private Integer idUser;

    @Transient
    private IUbigeo ubigeoAll;

    @ManyToOne
    @JoinColumn(name = "id_type_risk_place")
    private TypeRiskPlace typeRiskPlace;
}
