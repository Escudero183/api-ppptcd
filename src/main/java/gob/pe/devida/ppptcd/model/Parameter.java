package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * File created by Linygn Escudero$ on 20/12/23$
 */
@Data
@Entity
@Table(name="parameter", schema = "security")
public class Parameter {
    @Id
    private Integer idParameter;

    private String key;

    private String value;
}
