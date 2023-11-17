package gob.pe.devida.ppptcd.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

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

    @Transient
    private IUbigeo ubigeoAll;

    @ManyToOne
    @JoinColumn(name = "id_type_institution")
    private TypeInstitution typeInstitution;

    @OneToMany(mappedBy = "idInstitution")
    @Where(clause = "status = true")
    private List<InstitutionDirectory> directorio;

}
