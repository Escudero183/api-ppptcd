package gob.pe.devida.ppptcd.controller.v1;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gob.pe.devida.ppptcd.config.exception.ApiPPPTCDException;
import gob.pe.devida.ppptcd.model.Module;
import gob.pe.devida.ppptcd.model.Permission;
import gob.pe.devida.ppptcd.model.Profile;
import gob.pe.devida.ppptcd.model.User;
import gob.pe.devida.ppptcd.service.ModuleService;
import gob.pe.devida.ppptcd.service.PermissionService;
import gob.pe.devida.ppptcd.service.ProfileService;
import gob.pe.devida.ppptcd.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/security", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SecurityController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/* Servicios para la Entidad Profile */
	@ApiOperation(value = "Crea un Perfil", authorizations = {@Authorization(value = "apiKey") })	
	@PostMapping(value = "/profile")
	public ResponseEntity<?> saveProfile(@RequestBody Profile profile, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		profile.setStatus(true);
		Profile data = profileService.insert(profile);
		
		
		result.put("success", true);
		result.put("message", "Se ha registrado correctamente.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todos los Perfiles", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/profile")	
	public ResponseEntity<?> findAllProfile(
			@RequestParam(value = "query", required = false, defaultValue = "") String query,
			@RequestParam(value = "page", required = false, defaultValue = "-1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
			@RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
			HttpServletRequest request) {
		
		int maxPage = 10;
		
		if (page == -1 && limit == -1 && "".equals(sortBy)) {
			page = 1;
			limit = maxPage;		
		}else if (limit != -1 && page == -1) {
			page = 1;
		} else if (page != -1 && limit == -1) {
			limit = maxPage;
		}
		
		return new ResponseEntity<>(profileService.findAll(query, page, limit, sortBy), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtiene datos de un Perfil", authorizations = { @Authorization(value = "apiKey")})
	@GetMapping(value = "/profile/{idProfile}")
	public ResponseEntity<?> findProfile(@PathVariable(value = "idProfile") Integer idProfile, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		Profile data = profileService.findById(idProfile);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Perfil con código: " + idProfile);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		
		result.put("success", true);
		result.put("message", "Se ha encontrado el registro.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Actualiza un Perfil", authorizations = { @Authorization(value = "apiKey")})
	@PutMapping(value = "/profile")
	public ResponseEntity<?> updateProfile (@RequestBody Profile profile, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		Profile data = profileService.findById(profile.getIdProfile());
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Perfil con código: " + profile.getIdProfile());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			profile.setStatus(true);
			profileService.update(profile);
			result.put("success", true);
			result.put("message", "Se ha actualizado los datos del registro.");
			result.put("result", profile);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@ApiOperation(value = "Elimina un Perfil", authorizations = { @Authorization(value = "apiKey")})
	@DeleteMapping(value = "/profile/{idProfile}")
	public ResponseEntity<?> deleteProfile (@PathVariable(value = "idProfile") Integer idProfile, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		Profile data = profileService.findById(idProfile);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Perfil con código: " + idProfile);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			data.setStatus(false);
			profileService.delete(data);
			result.put("success", true);
			result.put("message", "Se ha eliminado los datos del registro.");
			result.put("result", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}	
	/* Fin Servicios Entidad Profile */
	
	
	/* Servicios para la Entidad User */
	@ApiOperation(value = "Crea un Usuario", authorizations = {@Authorization(value = "apiKey") })	
	@PostMapping(value = "/user")
	public ResponseEntity<?> saveUser(@RequestBody User user, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		
		if(userService.findByLogin(user.getLogin().toUpperCase()) != null) {
			result.put("success", false);
			result.put("message", "Ya existe un registro con el Login.");				
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);				
		}
		user.setPassword(passwordEncoder.encode(user.getLogin()));
		user.setStatus(true);
		User data = userService.insert(user);
				
		result.put("success", true);
		result.put("message", "Se ha registrado correctamente.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todas los Usuarios", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/user")	
	public ResponseEntity<?> findAllUser(
			@RequestParam(value = "query", required = false, defaultValue = "") String query,
			@RequestParam(value = "page", required = false, defaultValue = "-1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
			@RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
			HttpServletRequest request) {
		
		int maxPage = 10;
		
		if (page == -1 && limit == -1 && "".equals(sortBy)) {
			page = 1;
			limit = maxPage;		
		}else if (limit != -1 && page == -1) {
			page = 1;
		} else if (page != -1 && limit == -1) {
			limit = maxPage;
		}
		
		return new ResponseEntity<>(userService.findAll(query, page, limit, sortBy), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtiene datos de un Usuario", authorizations = { @Authorization(value = "apiKey")})
	@GetMapping(value = "/user/{idUser}")
	public ResponseEntity<?> findUser(@PathVariable(value = "idUser") Integer idUser, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		User data = userService.findById(idUser);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Usuario con código: " + idUser);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		
		result.put("success", true);
		result.put("message", "Se ha encontrado el registro.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Actualiza un Usuario", authorizations = { @Authorization(value = "apiKey")})
	@PutMapping(value = "/user")
	public ResponseEntity<?> updateUser (@RequestBody User user, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		User data = userService.findById(user.getIdUser());
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Usuario con código: " + user.getIdUser());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			user.setPassword(data.getPassword());
			user.setStatus(true);
			userService.update(user);
			result.put("success", true);
			result.put("message", "Se ha actualizado los datos del registro.");
			result.put("result", user);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@ApiOperation(value = "Actualiza Contraseña de un Usuario", authorizations = { @Authorization(value = "apiKey")})
	@PutMapping(value = "/user/resetPassword")
	public ResponseEntity<?> updatePasswordUser (@RequestBody User user, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		User data = userService.findById(user.getIdUser());
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Usuario con código: " + user.getIdUser());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			userService.resetPassword(user.getIdUser(), passwordEncoder.encode(user.getPassword()));
			result.put("success", true);
			result.put("message", "Se ha actualizado la contraseña del usuario.");
			result.put("result", user);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@ApiOperation(value = "Elimina un Usuario", authorizations = { @Authorization(value = "apiKey")})
	@DeleteMapping(value = "/user/{idUser}")
	public ResponseEntity<?> deleteUser (@PathVariable(value = "idUser") Integer idUser, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		User data = userService.findById(idUser);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Usuario con código: " + idUser);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			data.setStatus(false);
			userService.delete(data);
			result.put("success", true);
			result.put("message", "Se ha eliminado los datos del registro.");
			result.put("result", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}	
	/* Fin Servicios Entidad User */
	
	/* Servicios para la Entidad Module */
	@ApiOperation(value = "Crea un Módulo", authorizations = {@Authorization(value = "apiKey") })	
	@PostMapping(value = "/module")
	public ResponseEntity<?> saveModule(@RequestBody Module module, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		module.setStatus(true);
		Module data = moduleService.insert(module);
		
		
		result.put("success", true);
		result.put("message", "Se ha registrado correctamente.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todos los Módulos asociados a sus submódulos", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/module/list")	
	public ResponseEntity<?> listModule(
			@RequestParam(value = "idProfile", required = false, defaultValue = "-1") Integer idProfile,
			HttpServletRequest request) {
		return new ResponseEntity<>(moduleService.listModules(idProfile), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista Módulos padres o submodulos de un padre", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/module/{idModuleParent}")	
	public ResponseEntity<?> findModuleParent(@PathVariable(value = "idModuleParent") Integer idModuleParent, HttpServletRequest request) {
		return new ResponseEntity<>(moduleService.findModuleParent(idModuleParent), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todos los Módulos", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/module")	
	public ResponseEntity<?> findAllModule(
			@RequestParam(value = "query", required = false, defaultValue = "") String query,
			@RequestParam(value = "page", required = false, defaultValue = "-1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
			@RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
			HttpServletRequest request) {
		
		int maxPage = 10;
		
		if (page == -1 && limit == -1 && "".equals(sortBy)) {
			page = 1;
			limit = maxPage;		
		}else if (limit != -1 && page == -1) {
			page = 1;
		} else if (page != -1 && limit == -1) {
			limit = maxPage;
		}
		
		return new ResponseEntity<>(moduleService.findAll(query, page, limit, sortBy), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtiene datos de un Módulo", authorizations = { @Authorization(value = "apiKey")})
	@GetMapping(value = "/module/{idModule}")
	public ResponseEntity<?> findModule(@PathVariable(value = "idModule") Integer idModule, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		Module data = moduleService.findById(idModule);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Módulo con código: " + idModule);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		
		result.put("success", true);
		result.put("message", "Se ha encontrado el registro.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Actualiza un Módulo", authorizations = { @Authorization(value = "apiKey")})
	@PutMapping(value = "/module")
	public ResponseEntity<?> updateModule (@RequestBody Module module, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		Module data = moduleService.findById(module.getIdModule());
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Módulo con código: " + module.getIdModule());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			module.setStatus(true);
			moduleService.update(module);
			result.put("success", true);
			result.put("message", "Se ha actualizado los datos del registro.");
			result.put("result", module);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@ApiOperation(value = "Elimina un Módulo", authorizations = { @Authorization(value = "apiKey")})
	@DeleteMapping(value = "/module/{idModule}")
	public ResponseEntity<?> deleteModule (@PathVariable(value = "idModule") Integer idModule, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		Module data = moduleService.findById(idModule);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Módulo con código: " + idModule);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			data.setStatus(false);
			moduleService.delete(data);
			result.put("success", true);
			result.put("message", "Se ha eliminado los datos del registro.");
			result.put("result", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}	
	/* Fin Servicios Entidad Module */
	
	/* Servicios para la Entidad Permission */
	@ApiOperation(value = "Crea o actualiza Permisos", authorizations = {@Authorization(value = "apiKey") })	
	@PostMapping(value = "/permission")
	public ResponseEntity<?> savePermission(@RequestBody Permission permission, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		Permission isExist = permissionService.findByProfileAndModule(permission.getIdProfile(), permission.getIdModule());
		if(isExist != null) {
			permission.setIdPermission(isExist.getIdPermission());
		}
		
		permission.setStatus(true);
		permission.setCreatedAt(Date.valueOf(LocalDate.now()));
		Permission data = permissionService.insert(permission);
		
		
		result.put("success", true);
		result.put("message", "Se ha registrado correctamente.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todos los permisos de un Perfil", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/permission")	
	public ResponseEntity<?> findAllModule(
			@RequestParam(value = "idProfile", required = false, defaultValue = "-1") Integer idProfile,
			HttpServletRequest request) {
		
		return new ResponseEntity<>(permissionService.findByProfile(idProfile), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Elimina un Permiso", authorizations = { @Authorization(value = "apiKey")})
	@DeleteMapping(value = "/permission/{idPermission}")
	public ResponseEntity<?> deletePermission (@PathVariable(value = "idPermission") Integer idPermission, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		Permission data = permissionService.findById(idPermission);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Permiso con código: " + idPermission);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			data.setStatus(false);
			permissionService.delete(data);
			result.put("success", true);
			result.put("message", "Se ha eliminado los datos del registro.");
			result.put("result", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	/* Fin Servicios Entidad Permission */

}
