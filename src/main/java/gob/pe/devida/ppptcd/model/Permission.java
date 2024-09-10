package gob.pe.devida.ppptcd.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Data
@Entity
@Table(name = "permission", schema = "security")
public class Permission {
	@Id
    @SequenceGenerator(name = "permission_generator_seq", sequenceName = "security.permission_id_permission_seq", allocationSize = 1)
    @GeneratedValue(generator = "permission_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idPermission;

    private Integer idProfile;
    
    private Integer idModule;
    
    private Date createdAt;

    private boolean status;

}
