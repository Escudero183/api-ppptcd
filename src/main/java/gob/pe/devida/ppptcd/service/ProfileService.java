package gob.pe.devida.ppptcd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import gob.pe.devida.ppptcd.model.Profile;
import gob.pe.devida.ppptcd.repository.ProfileRepository;

@Service
public class ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;
	
	public Profile insert(Profile item) {
        return profileRepository.save(item);
    }

    public void update(Profile item) {
    	profileRepository.save(item);
    }

    public void delete (Profile item) {
    	profileRepository.delete(item);
    }

    public Profile findById(Integer idUser) {
        return profileRepository.findById(idUser).orElse(null);
    }
    
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }
    
    public List<Profile> findAll(String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Direction.ASC, "idProfile");
        }
        return profileRepository.findAll("%" + query.toLowerCase() + "%", sort);
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
            Sort sort = Sort.by(Direction.ASC, "idProfile");
            pageable = PageRequest.of(page - 1, limit, sort);

        }
        Page<Profile> data = profileRepository.findAllParams("%" + query.toLowerCase() + "%", pageable);
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

}
