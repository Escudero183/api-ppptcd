package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.TypeInstitution;
import gob.pe.devida.ppptcd.repository.TypeInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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

    public List<TypeInstitution> findAll(String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Direction.ASC, "idTypeInstitution");
        }
        return typeInstitutionRepository.findAll("%" + query.toLowerCase() + "%", sort);
    }

    public HashMap<String, Object> findAll(String query, int page, int limit, String sortBy) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Pageable pageable;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();

            Sort sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
            pageable = PageRequest.of(page - 1, limit, sort);

        } else {
            Sort sort = Sort.by(Direction.ASC, "idTypeInstitution");
            pageable = PageRequest.of(page - 1, limit, sort);

        }
        Page<TypeInstitution> data = typeInstitutionRepository.findAllParams("%" + query.toLowerCase() + "%", pageable);
        if (!data.getContent().isEmpty()) {
            result.put("items", data.getContent());
        } else {
            result.put("items", new ArrayList<>());
        }
        result.put("totalPage", data.getTotalPages());
        result.put("totalRows", data.getNumberOfElements());
        result.put("totalItems", data.getTotalElements());
        result.put("page", page);
        result.put("sizeRows", limit);
        return result;
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
