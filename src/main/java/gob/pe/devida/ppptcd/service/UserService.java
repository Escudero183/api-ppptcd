package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.User;
import gob.pe.devida.ppptcd.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User insert(User item) {
        return userRepository.save(item);
    }

    public void update(User item) {
        userRepository.save(item);
    }

    public void delete (User item) {
        userRepository.delete(item);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findById(Integer idUser) {
        return userRepository.findById(idUser).orElse(null);
    }

    public void resetPassword(Integer idUser, String password) {
        userRepository.resetPassword(idUser, password);
    }
    
    public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}
    
    public List<User> findAll(List<Integer> users){
		return userRepository.findAll(users);
	}
    
    public List<User> findAll(String query){
		return userRepository.findAll("%" + query.toLowerCase() + "%");
	}
    
    public List<User> findAll(String query, String sortBy) {
		Sort sort;
		if (!sortBy.equals("")) {
			String sortColumn = sortBy.split("\\|")[0];
			String sortDirection = sortBy.split("\\|")[1].toUpperCase();
			sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
		} else {
			sort = Sort.by(Direction.ASC, "idUser");
		}
		return userRepository.findAll("%" + query.toLowerCase() + "%", sort);
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
			Sort sort = Sort.by(Direction.ASC, "idUser");
			pageable = PageRequest.of(page - 1, limit, sort);

		}
		Page<User> data = userRepository.findAllParams("%" + query.toLowerCase() + "%", pageable);
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
