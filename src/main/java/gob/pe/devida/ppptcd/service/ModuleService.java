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

import gob.pe.devida.ppptcd.model.EducationalInstitution;
import gob.pe.devida.ppptcd.model.Module;
import gob.pe.devida.ppptcd.model.Permission;
import gob.pe.devida.ppptcd.repository.ModuleRepository;
import gob.pe.devida.ppptcd.repository.PermissionRepository;

@Service
public class ModuleService {
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	public Module insert(Module item) {
        return moduleRepository.save(item);
    }

    public void update(Module item) {
    	moduleRepository.save(item);
    }

    public void delete (Module item) {
    	moduleRepository.save(item);
    }

    public Module findById(Integer idUser) {
        return moduleRepository.findById(idUser).orElse(null);
    }
    
    public List<Module> findModuleParent(Integer idModuleParent) {
        return moduleRepository.findModuleParent(idModuleParent);
    }
    
    public List<Module> listModules(Integer idProfile) {
        List<Module> parents = moduleRepository.findModuleParent(0);

        for (Module parent : parents) {
            List<Module> subModules = getSubModulesWithPermissions(idProfile, parent.getIdModule());
            parent.setSubModules(subModules);
            
            if (idProfile != -1) {
                parent.setPermission(hasPermission(idProfile, parent.getIdModule()));
            }
        }
        return parents;
    }

    private List<Module> getSubModulesWithPermissions(Integer idProfile, Integer parentModuleId) {
        List<Module> subModules = moduleRepository.findModuleParent(parentModuleId);

        if (idProfile != -1) {
            for (Module subModule : subModules) {
                subModule.setPermission(hasPermission(idProfile, subModule.getIdModule()));
            }
        }
        return subModules;
    }

    private boolean hasPermission(Integer idProfile, Integer moduleId) {
        List<Permission> permissions = permissionRepository.findByProfileAndModule(idProfile, moduleId);
        return !permissions.isEmpty();
    }
    
    public List<Module> findAll() {
        return moduleRepository.findAll();
    }
    
    public List<Module> findAll(String query, String sortBy) {
        Sort sort;
        if (!sortBy.equals("")) {
            String sortColumn = sortBy.split("\\|")[0];
            String sortDirection = sortBy.split("\\|")[1].toUpperCase();
            sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
        } else {
            sort = Sort.by(Direction.ASC, "idModule");
        }
        return moduleRepository.findAll("%" + query.toLowerCase() + "%", sort);
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
            Sort sort = Sort.by(Direction.ASC, "idModule");
            pageable = PageRequest.of(page - 1, limit, sort);

        }
        Page<Module> data = moduleRepository.findAllParams("%" + query.toLowerCase() + "%", pageable);
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
