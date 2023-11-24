package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.Treatment;
import gob.pe.devida.ppptcd.repository.TreatmentRepository;
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
public class TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

    public Treatment insert(Treatment item) {
        return treatmentRepository.save(item);
    }

    public List<Treatment> findAll() {
        return treatmentRepository.findAll();
    }

    public List<Treatment> findAll(Integer idStudent, Integer idSpecialist, String registrationDate, String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Sort.Direction.ASC, "idTreatment");
        }
        return treatmentRepository.findAll(idStudent, idSpecialist, registrationDate, "%" + query.toLowerCase() + "%", sort);
    }

    public HashMap<String, Object> findAll(Integer idStudent, Integer idSpecialist, String registrationDate, String query, int page, int limit, String sortBy) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Pageable pageable;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();

            Sort sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
            pageable = PageRequest.of(page - 1, limit, sort);

        } else {
            Sort sort = Sort.by(Sort.Direction.ASC, "idTreatment");
            pageable = PageRequest.of(page - 1, limit, sort);

        }
        Page<Treatment> data = treatmentRepository.findAllParams(idStudent, idSpecialist, registrationDate,"%" + query.toLowerCase() + "%", pageable);
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

    public Treatment findById(Integer id) {
        return treatmentRepository.findById(id).orElse(null);
    }

    public void update(Treatment item) {
        treatmentRepository.save(item);
    }

    public void delete(Treatment item) {
        treatmentRepository.save(item);
    }
}
