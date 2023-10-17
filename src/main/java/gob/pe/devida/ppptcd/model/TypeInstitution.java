package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Data
@Entity
@Table(name = "type_institution", schema = "setting")
public class TypeInstitution {

    @Id
    @SequenceGenerator(name = "type_institution_generator_seq", sequenceName = "setting.type_institution_id_type_institution_seq", allocationSize = 1)
    @GeneratedValue(generator = "type_institution_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idTypeInstitution;

    private String description;

    private String abbreviation;

}
