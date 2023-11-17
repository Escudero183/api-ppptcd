package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 16/11/2023$
 */
@Data
@Entity
@Table(name = "institution_directory", schema = "ppptcd")
public class InstitutionDirectory {

    @Id
    @SequenceGenerator(name = "institution_directory_generator_seq", sequenceName = "ppptcd.institution_directory_id_institution_directory_seq", allocationSize = 1)
    @GeneratedValue(generator = "institution_directory_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idInstitutionDirectory;

    private String firstName;

    private String lastNameOne;

    private String lastNameTwo;

    private String dni;

    private String position;

    private String phone;

    private String email;

    private String photo;

    private boolean status;

    private Integer idInstitution;
}
