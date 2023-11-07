package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.Ubigeo;
import gob.pe.devida.ppptcd.repository.UbigeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * File created by Linygn Escudero$ on 02/11/2023$
 */

@Service
public class UbigeoService {

    @Autowired
    UbigeoRepository ubigeoRepository;

    public Ubigeo findById(String id) {
        return ubigeoRepository.findById(id).orElse(null);
    }
}
