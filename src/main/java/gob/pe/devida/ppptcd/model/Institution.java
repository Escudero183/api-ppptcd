package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 18/10/2023$
 */

@Data
@Entity
@Table(name = "institution", schema = "ppptcd")
public class Institution {

    @Id
    @SequenceGenerator(name = "institution_generator_seq", sequenceName = "ppptcd.institution_id_institution_seq", allocationSize = 1)
    @GeneratedValue(generator = "institution_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idInstitution;

    private String name;

    private String address;

    private String ubigeo;

    private String phone;

    private String additionalData;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "id_type_institution")
    private TypeInstitution typeInstitution;

}
