package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.EducationalInstitutionDirectory;
import gob.pe.devida.ppptcd.repository.EducationalInstitutionDirectoryRepository;
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
 * File created by Linygn Escudero$ on 12/11/2023$
 */
@Service
public class EducationalInstitutionDirectoryService {

    @Autowired
    private EducationalInstitutionDirectoryRepository educationalInstitutionDirectoryRepository;

    public EducationalInstitutionDirectory insert(EducationalInstitutionDirectory item) {
        return educationalInstitutionDirectoryRepository.save(item);
    }

    public List<EducationalInstitutionDirectory> findAll() {
        return educationalInstitutionDirectoryRepository.findAll();
    }

    public List<EducationalInstitutionDirectory> findAll(Integer idEducationalInstitution, String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Sort.Direction.ASC, "idEducationalInstitution");
        }
        return educationalInstitutionDirectoryRepository.findAll(idEducationalInstitution, "%" + query.toLowerCase() + "%", sort);
    }

    public HashMap<String, Object> findAll(Integer idEducationalInstitution, String query, int page, int limit, String sortBy) {
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
        Page<EducationalInstitutionDirectory> data = educationalInstitutionDirectoryRepository.findAllParams(idEducationalInstitution, "%" + query.toLowerCase() + "%", pageable);
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

    public EducationalInstitutionDirectory findById(Integer id) {
        return educationalInstitutionDirectoryRepository.findById(id).orElse(null);
    }

    public void update(EducationalInstitutionDirectory item) {
        educationalInstitutionDirectoryRepository.save(item);
    }

    public void delete(EducationalInstitutionDirectory item) {
        educationalInstitutionDirectoryRepository.save(item);
    }
}
