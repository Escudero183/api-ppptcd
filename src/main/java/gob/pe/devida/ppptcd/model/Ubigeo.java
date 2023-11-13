package gob.pe.devida.ppptcd.model;

import lombok.Data;

import javax.persistence.*;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Data
@Entity
@Table(name = "ubigeo", schema = "setting")
public class Ubigeo {

    @Id
    private String idUbigeo;

    private String description;

}
