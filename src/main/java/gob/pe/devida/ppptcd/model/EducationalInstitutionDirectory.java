package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 12/11/2023$
 */
@Data
@Entity
@Table(name = "educational_institution_directory", schema = "ppptcd")
public class EducationalInstitutionDirectory {

    @Id
    @SequenceGenerator(name = "educational_institution_directory_generator_seq", sequenceName = "ppptcd.educational_institution_id_educational_institution_seq", allocationSize = 1)
    @GeneratedValue(generator = "educational_institution_directory_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idEducationalInstitutionDirectory;

    private String firstName;

    private String lastNameOne;

    private String lastNameTwo;

    private String position;

    private String phone;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "id_educational_institution")
    private EducationalInstitution educationalInstitution;
}
