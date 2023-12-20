package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.Parameter;
import gob.pe.devida.ppptcd.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * File created by Linygn Escudero$ on 20/12/23$
 */
@Service
public class ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    public Parameter find(Integer idParameter) {
        return parameterRepository.findById(idParameter).orElse(null);
    }

}
