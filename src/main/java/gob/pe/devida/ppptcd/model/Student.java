package gob.pe.devida.ppptcd.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * File created by Linygn Escudero$ on 22/11/2023$
 */

@Data
@Entity
@Table(name = "student", schema = "ppptcd")
public class Student {
    @Id
    @SequenceGenerator(name = "student_generator_seq", sequenceName = "ppptcd.student_id_student_seq", allocationSize = 1)
    @GeneratedValue(generator = "student_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idStudent;

    private Integer idUserReport;

    private Integer idEducationalInstitution;

    private String firstName;

    private String lastNameOne;

    private String lastNameTwo;

    private String sex;

    private Date birthdate;

    private String degree;

    private String section;

    private String photo;

    private String phone;

    private String email;

    private String fistNameAttorney;

    private String lastNameOneAttorney;

    private String lastNameTwoAttorney;

    private String dniAttorney;

    private String photoAttorney;

    private String phoneAttorney;

    private String emailAttorney;

    private String ubigeo;

    private String address;

    private String stateEvolution;

    private Date registrationDate;

    private boolean status;

    @Transient
    private IUbigeo ubigeoAll;

    @ManyToOne
    @JoinColumn(name = "idEducationalInstitutionDirectory")
    private EducationalInstitutionDirectory tutor;

    @OneToMany(mappedBy = "idStudent")
    @Where(clause = "status = true")
    private List<Treatment> treatments;

    @OneToMany(mappedBy = "idStudent")
    @Where(clause = "status = true")
    private List<Incidence> incidences;
}
