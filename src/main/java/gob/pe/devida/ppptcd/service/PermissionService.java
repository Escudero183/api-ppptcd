package gob.pe.devida.ppptcd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gob.pe.devida.ppptcd.model.Permission;
import gob.pe.devida.ppptcd.repository.PermissionRepository;

@Service
public class PermissionService {
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	public Permission insert(Permission item) {
        return permissionRepository.save(item);
    }

    public void update(Permission item) {
    	permissionRepository.save(item);
    }

    public void delete (Permission item) {
    	permissionRepository.save(item);
    }

    public Permission findById(Integer idUser) {
        return permissionRepository.findById(idUser).orElse(null);
    }
    
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }
    
    public List<Permission> findByProfile(Integer idProfile) {
        return permissionRepository.findByProfile(idProfile);
    }
    
    public Permission findByProfileAndModule(Integer idProfile, Integer idModule) {
    	List<Permission> permissions = permissionRepository.findByProfileAndModule(idProfile, idModule);
        
        if (permissions.isEmpty()) {
            return null;
        }
        
        return permissions.get(0);
    }

}
