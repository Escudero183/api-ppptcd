package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.TypeRiskPlace;
import gob.pe.devida.ppptcd.repository.TypeRiskPlaceRepository;
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
public class TypeRiskPlaceService {
    
    @Autowired
    private TypeRiskPlaceRepository typeRiskPlaceRepository;

    public TypeRiskPlace insert(TypeRiskPlace item) {
        return typeRiskPlaceRepository.save(item);
    }

    public List<TypeRiskPlace> findAll() {
        return typeRiskPlaceRepository.findAll();
    }

    public List<TypeRiskPlace> findAll(String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Sort.Direction.ASC, "idTypeRiskPlace");
        }
        return typeRiskPlaceRepository.findAll("%" + query.toLowerCase() + "%", sort);
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
            Sort sort = Sort.by(Sort.Direction.ASC, "idTypeRiskPlace");
            pageable = PageRequest.of(page - 1, limit, sort);

        }
        Page<TypeRiskPlace> data = typeRiskPlaceRepository.findAllParams("%" + query.toLowerCase() + "%", pageable);
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

    public TypeRiskPlace findById(Integer id) {
        return typeRiskPlaceRepository.findById(id).orElse(null);
    }

    public void update(TypeRiskPlace item) {
        typeRiskPlaceRepository.save(item);
    }

    public void delete(TypeRiskPlace item) {
        typeRiskPlaceRepository.save(item);
    }
}
