package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.Incidence;
import gob.pe.devida.ppptcd.repository.IncidenceRepository;
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
 * File created by Linygn Escudero$ on 22/11/2023$
 */
@Service
public class IncidenceService {

    @Autowired
    private IncidenceRepository incidenceRepository;

    public Incidence insert(Incidence item) {
        return incidenceRepository.save(item);
    }

    public List<Incidence> findAll() {
        return incidenceRepository.findAll();
    }

    public List<Incidence> findAll(Integer idStudent, String registrationDate, String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Sort.Direction.ASC, "idIncidence");
        }
        return incidenceRepository.findAll(idStudent, registrationDate, "%" + query.toLowerCase() + "%", sort);
    }

    public HashMap<String, Object> findAll(Integer idStudent, String registrationDate, String query, int page, int limit, String sortBy) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Pageable pageable;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();

            Sort sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
            pageable = PageRequest.of(page - 1, limit, sort);

        } else {
            Sort sort = Sort.by(Sort.Direction.ASC, "idIncidence");
            pageable = PageRequest.of(page - 1, limit, sort);

        }
        Page<Incidence> data = incidenceRepository.findAllParams(idStudent, registrationDate,"%" + query.toLowerCase() + "%", pageable);
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

    public Incidence findById(Integer id) {
        return incidenceRepository.findById(id).orElse(null);
    }

    public void update(Incidence item) {
        incidenceRepository.save(item);
    }

    public void delete(Incidence item) {
        incidenceRepository.save(item);
    }
}
