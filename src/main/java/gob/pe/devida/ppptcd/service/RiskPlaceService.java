package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.EducationalInstitution;
import gob.pe.devida.ppptcd.model.RiskPlace;
import gob.pe.devida.ppptcd.repository.RiskPlaceRepository;
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
public class RiskPlaceService {
    
    @Autowired
    private RiskPlaceRepository riskPlaceRepository;

    @Autowired
    private UbigeoRepository ubigeoRepository;

    public RiskPlace insert(RiskPlace item) {
        return riskPlaceRepository.save(item);
    }

    public List<RiskPlace> findAll() {
        return riskPlaceRepository.findAll();
    }

    public List<RiskPlace> findAll(String registrationStatus, String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Sort.Direction.ASC, "idRiskPlace");
        }
        return riskPlaceRepository.findAll("%" + query.toLowerCase() + "%", registrationStatus, sort);
    }

    public HashMap<String, Object> findAll(String registrationStatus, String query, int page, int limit, String sortBy) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Pageable pageable;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();

            Sort sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
            pageable = PageRequest.of(page - 1, limit, sort);

        } else {
            Sort sort = Sort.by(Sort.Direction.ASC, "idRiskPlace");
            pageable = PageRequest.of(page - 1, limit, sort);

        }
        Page<RiskPlace> data = riskPlaceRepository.findAllParams("%" + query.toLowerCase() + "%", registrationStatus, pageable);
        if (!data.getContent().isEmpty()) {
            for(RiskPlace ei : data) {
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

    public RiskPlace findById(Integer id) {
        return riskPlaceRepository.findById(id).orElse(null);
    }

    public void update(RiskPlace item) {
        riskPlaceRepository.save(item);
    }

    public void delete(RiskPlace item) {
        riskPlaceRepository.save(item);
    }
}
