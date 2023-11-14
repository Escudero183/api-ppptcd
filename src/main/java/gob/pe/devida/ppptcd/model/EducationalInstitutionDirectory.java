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
    @SequenceGenerator(name = "educational_institution_directory_generator_seq", sequenceName = "ppptcd.educational_institution_direc_id_educational_institution_di_seq", allocationSize = 1)
    @GeneratedValue(generator = "educational_institution_directory_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idEducationalInstitutionDirectory;

    private String firstName;

    private String lastNameOne;

    private String lastNameTwo;

    private String dni;

    private String position;

    private String phone;

    private String email;

    private String photo;

    private boolean status;

    private Integer idEducationalInstitution;
}
