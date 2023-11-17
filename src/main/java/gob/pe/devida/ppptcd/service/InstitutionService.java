package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.EducationalInstitution;
import gob.pe.devida.ppptcd.model.Institution;
import gob.pe.devida.ppptcd.repository.InstitutionRepository;
import gob.pe.devida.ppptcd.repository.UbigeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * File created by Linygn Escudero$ on 24/10/2023$
 */

@Service
public class InstitutionService {
    
    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private UbigeoRepository ubigeoRepository;

    public Institution insert(Institution item) {
        return institutionRepository.save(item);
    }

    public List<Institution> findAll() {
        return institutionRepository.findAll();
    }

    public List<Institution> findAll(Integer idTypeInstitution, String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Sort.Direction.ASC, "idInstitution");
        }
        return institutionRepository.findAll("%" + query.toLowerCase() + "%", idTypeInstitution, sort);
    }

    public HashMap<String, Object> findAll(Integer idTypeInstitution, String query, int page, int limit, String sortBy) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Pageable pageable;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();

            Sort sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
            pageable = PageRequest.of(page - 1, limit, sort);

        } else {
            Sort sort = Sort.by(Sort.Direction.ASC, "idInstitution");
            pageable = PageRequest.of(page - 1, limit, sort);

        }
        Page<Institution> data = institutionRepository.findAllParams("%" + query.toLowerCase() + "%", idTypeInstitution, pageable);
        if (!data.getContent().isEmpty()) {
            for(Institution ia : data) {
                ia.setUbigeoAll(ubigeoRepository.getUbigeoFull(ia.getUbigeo()));
            }
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

    public Institution findById(Integer id) {
        return institutionRepository.findById(id).orElse(null);
    }

    public void update(Institution item) {
        institutionRepository.save(item);
    }

    public void delete(Institution item) {
        institutionRepository.save(item);
    }
}
