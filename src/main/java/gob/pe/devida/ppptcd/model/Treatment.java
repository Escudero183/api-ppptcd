package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * File created by Linygn Escudero$ on 22/11/2023$
 */
@Data
@Entity
@Table(name = "treatment", schema = "ppptcd")
public class Treatment {
    @Id
    @SequenceGenerator(name = "treatment_generator_seq", sequenceName = "ppptcd.treatment_id_treatment_seq", allocationSize = 1)
    @GeneratedValue(generator = "treatment_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idTreatment;

    private Date registrationDate;

    private Integer idSpecialist;

    private Integer idStudent;

    private String admissionReason;

    private String diagnosis;

    private String treatmentSummary;

    private String archive;

    private boolean status;
}
