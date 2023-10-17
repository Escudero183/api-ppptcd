package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.TypeInstitution;
import gob.pe.devida.ppptcd.repository.TypeInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeInstitutionService {

    @Autowired
    private TypeInstitutionRepository typeInstitutionRepository;

    public TypeInstitution insert(TypeInstitution item) {
        return typeInstitutionRepository.save(item);
    }

    public List<TypeInstitution> findAll() {
        return typeInstitutionRepository.findAll();
    }

    public TypeInstitution findById(Integer id) {
        return typeInstitutionRepository.findById(id).orElse(null);
    }

    public void update(TypeInstitution item) {
        typeInstitutionRepository.save(item);
    }

    public void delete(TypeInstitution item) {
        typeInstitutionRepository.save(item);
    }

}
