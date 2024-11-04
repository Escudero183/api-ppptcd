package gob.pe.devida.ppptcd.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Data
@Entity
@Table(name = "module", schema = "security")
public class Module {
	@Id
    @SequenceGenerator(name = "module_generator_seq", sequenceName = "security.module_id_module_seq", allocationSize = 1)
    @GeneratedValue(generator = "module_generator_seq", strategy = GenerationType.SEQUENCE)
    private Integer idModule;

    private String name;
    
    private String path;
    
    private Integer idModuleParent;
    
    private Integer order;

    private boolean status;
    
    @Transient
    private List<Module> subModules;
    
    @Transient
    private Boolean permission;

}
