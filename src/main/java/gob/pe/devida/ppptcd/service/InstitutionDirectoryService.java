package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.EducationalInstitutionDirectory;
import gob.pe.devida.ppptcd.model.InstitutionDirectory;
import gob.pe.devida.ppptcd.repository.InstitutionDirectoryRepository;
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
 * File created by Linygn Escudero$ on 16/11/2023$
 */
@Service
public class InstitutionDirectoryService {

    @Autowired
    private InstitutionDirectoryRepository institutionDirectoryRepository;

    public InstitutionDirectory insert(InstitutionDirectory item) {
        return institutionDirectoryRepository.save(item);
    }

    public List<InstitutionDirectory> findAll() {
        return institutionDirectoryRepository.findAll();
    }

    public List<InstitutionDirectory> findAll(Integer idInstitution, String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Sort.Direction.ASC, "idInstitutionDirectory");
        }
        return institutionDirectoryRepository.findAll(idInstitution, "%" + query.toLowerCase() + "%", sort);
    }

    public HashMap<String, Object> findAll(Integer idInstitution, String query, int page, int limit, String sortBy) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Pageable pageable;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();

            Sort sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
            pageable = PageRequest.of(page - 1, limit, sort);

        } else {
            Sort sort = Sort.by(Sort.Direction.ASC, "idInstitutionDirectory");
            pageable = PageRequest.of(page - 1, limit, sort);

        }
        Page<InstitutionDirectory> data = institutionDirectoryRepository.findAllParams(idInstitution, "%" + query.toLowerCase() + "%", pageable);
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

    public InstitutionDirectory findById(Integer id) {
        return institutionDirectoryRepository.findById(id).orElse(null);
    }

    public void update(InstitutionDirectory item) {
        institutionDirectoryRepository.save(item);
    }

    public void delete(InstitutionDirectory item) {
        institutionDirectoryRepository.save(item);
    }
}
