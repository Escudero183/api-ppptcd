package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.EducationalInstitution;
import gob.pe.devida.ppptcd.model.EducationalInstitution;
import gob.pe.devida.ppptcd.repository.EducationalInstitutionRepository;
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
 * File created by Linygn Escudero$ on 17/10/2023$
 */
@Service
public class EducationalInstitutionService {

    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;

    @Autowired
    private UbigeoRepository ubigeoRepository;

    public EducationalInstitution insert(EducationalInstitution item) {
        return educationalInstitutionRepository.save(item);
    }

    public List<EducationalInstitution> findAll() {
        return educationalInstitutionRepository.findAll();
    }

    public List<EducationalInstitution> findAll(String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Sort.Direction.ASC, "idEducationalInstitution");
        }
        return educationalInstitutionRepository.findAll("%" + query.toLowerCase() + "%", sort);
    }

    public HashMap<String, Object> findAll(String query, int page, int limit, String sortBy) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Pageable pageable;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();

            Sort sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
            pageable = PageRequest.of(page - 1, limit, sort);

        } else {
            Sort sort = Sort.by(Sort.Direction.ASC, "idEducationalInstitution");
            pageable = PageRequest.of(page - 1, limit, sort);

        }
        Page<EducationalInstitution> data = educationalInstitutionRepository.findAllParams("%" + query.toLowerCase() + "%", pageable);

        if (!data.getContent().isEmpty()) {
            for(EducationalInstitution ei : data) {
                ei.setUbigeoAll(ubigeoRepository.getUbigeoFull(ei.getUbigeo()));
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
