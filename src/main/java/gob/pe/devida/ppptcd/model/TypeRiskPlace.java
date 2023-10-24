package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 18/10/2023$
 */

@Data
@Entity
@Table(name = "type_risk_place", schema = "setting")
public class TypeRiskPlace {

    @Id
    @SequenceGenerator(name = "type_risk_place_generator_seq", sequenceName = "setting.type_risk_place_id_type_risk_place_seq", allocationSize = 1)
    @GeneratedValue(generator = "type_risk_place_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idTypeRiskPlace;

    private String name;

    private String description;

    private String icon;

    private boolean status;
}
