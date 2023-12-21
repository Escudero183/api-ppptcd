package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.Institution;
import gob.pe.devida.ppptcd.model.Student;
import gob.pe.devida.ppptcd.repository.StudentRepository;
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
 * File created by Linygn Escudero$ on 22/11/2023$
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UbigeoRepository ubigeoRepository;

    public Student insert(Student item) {
        return studentRepository.save(item);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public List<Student> findAll(Integer idEducationalInstitution, String stateEvolution, String sex, String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Sort.Direction.ASC, "idStudent");
        }
        return studentRepository.findAll(idEducationalInstitution, stateEvolution, sex, "%" + query.toLowerCase() + "%", sort);
    }

    public HashMap<String, Object> findAll(List<Integer> idEducationalInstitution, String stateEvolution, String sex, String query, int page, int limit, String sortBy) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        Pageable pageable;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();

            Sort sort = Sort.by(sortDirection.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortColumn);
            pageable = PageRequest.of(page - 1, limit, sort);

        } else {
            Sort sort = Sort.by(Sort.Direction.ASC, "idStudent");
            pageable = PageRequest.of(page - 1, limit, sort);

        }
        Page<Student> data = studentRepository.findAllParams(idEducationalInstitution, stateEvolution, sex,"%" + query.toLowerCase() + "%", pageable);
        if (!data.getContent().isEmpty()) {
            for(Student ia : data) {
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

    public Student findById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void update(Student item) {
        studentRepository.save(item);
    }

    public void delete(Student item) {
        studentRepository.save(item);
    }
}
