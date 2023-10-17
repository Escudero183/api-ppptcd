package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.EducationalInstitution;
import gob.pe.devida.ppptcd.repository.EducationalInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */
@Service
public class EducationalInstitutionService {

    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;

    public EducationalInstitution insert(EducationalInstitution item) {
        return educationalInstitutionRepository.save(item);
    }

    public List<EducationalInstitution> findAll() {
        return educationalInstitutionRepository.findAll();
    }

    public EducationalInstitution findById(Integer id) {
        return educationalInstitutionRepository.findById(id).orElse(null);
    }

    public void update(EducationalInstitution item) {
        educationalInstitutionRepository.save(item);
    }

    public void delete(EducationalInstitution item) {
        educationalInstitutionRepository.save(item);
    }
}
