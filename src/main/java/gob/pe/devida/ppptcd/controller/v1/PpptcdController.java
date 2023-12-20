package gob.pe.devida.ppptcd.controller.v1;

import gob.pe.devida.ppptcd.config.exception.ApiPPPTCDException;
import gob.pe.devida.ppptcd.config.exception.RestException;
import gob.pe.devida.ppptcd.config.security.model.JwtUser;
import gob.pe.devida.ppptcd.model.*;
import gob.pe.devida.ppptcd.service.*;
import gob.pe.devida.ppptcd.utils.FileUtil;
import gob.pe.devida.ppptcd.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * File created by Linygn Escudero$ on 24/10/2023$
 */

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/ppptcd", produces = {MediaType.APPLICATION_JSON_VALUE})
public class PpptcdController {

    @Autowired
    private RiskPlaceService riskPlaceService;

    @Autowired
    private EducationalInstitutionService educationalInstitutionService;

    @Autowired
    private EducationalInstitutionDirectoryService educationalInstitutionDirectoryService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private InstitutionDirectoryService institutionDirectoryService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private IncidenceService incidenceService;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private ParameterService parameterService;

    @ApiOperation(value = "Lista todas las lugares de riesgo", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/risk_place")
    public ResponseEntity<?> findAllRiskPlace(
            @RequestParam(value = "registrationStatus", required = false, defaultValue = "") String registrationStatus,
            @RequestParam(value = "type", required = false, defaultValue = "grilla") String type,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "-1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
            @RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
            HttpServletRequest request) {

        if(type.equals("grilla")) {
            int maxPage = 10;

            if (page == -1 && limit == -1 && "".equals(sortBy)) {
                page = 1;
                limit = maxPage;
            }else if (limit != -1 && page == -1) {
                page = 1;
            } else if (page != -1 && limit == -1) {
                limit = maxPage;
            }

            return new ResponseEntity<>(riskPlaceService.findAll(registrationStatus, query, page, limit, sortBy), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(riskPlaceService.findAll(registrationStatus, query, sortBy), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Crea un lugar de riesgo", authorizations = {@Authorization(value = "apiKey") })
    @PostMapping(value = "/risk_place")
    public ResponseEntity<?> saveRiskPlace(@RequestBody RiskPlace data, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer idUser = 0;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            JwtUser userDetails = (JwtUser) auth.getPrincipal();
            idUser = userDetails.getId();

        } else {
            return new ResponseEntity<>(new RestException("No Autorizado"), HttpStatus.UNAUTHORIZED);
        }

        data.setIdUser(idUser);
        data.setStatus(true);
        RiskPlace result = riskPlaceService.insert(data);


        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualiza un lugar de riesgo", authorizations = { @Authorization(value = "apiKey")})
    @PutMapping(value = "/risk_place")
    public ResponseEntity<?> updateRiskPlace (@RequestBody RiskPlace data, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        RiskPlace dataInDB = riskPlaceService.findById(data.getIdRiskPlace());
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe lugar de riesgo con código: " + data.getIdRiskPlace());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            data.setStatus(true);
            riskPlaceService.update(data);
            result.put("success", true);
            result.put("message", "Se ha actualizado los datos del registro.");
            result.put("result", data);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina un lugar de riesgo", authorizations = { @Authorization(value = "apiKey")})
    @DeleteMapping(value = "/risk_place/{idRiskPlace}")
    public ResponseEntity<?> deleteRiskPlace (@PathVariable(value = "idRiskPlace") Integer idRiskPlace, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        RiskPlace dataInDB = riskPlaceService.findById(idRiskPlace);
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe lugar de riesgo con código: " + idRiskPlace);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            dataInDB.setStatus(false);
            riskPlaceService.delete(dataInDB);
            result.put("success", true);
            result.put("message", "Se ha eliminado los datos del registro.");
            result.put("result", dataInDB);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista todas las instituciones educativas", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/educational_institution")
    public ResponseEntity<?> findAllEducationalInstitution(
            @RequestParam(value = "type", required = false, defaultValue = "grilla") String type,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "-1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
            @RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
            HttpServletRequest request) {

        if(type.equals("grilla")) {
            int maxPage = 10;

            if (page == -1 && limit == -1 && "".equals(sortBy)) {
                page = 1;
                limit = maxPage;
            }else if (limit != -1 && page == -1) {
                page = 1;
            } else if (page != -1 && limit == -1) {
                limit = maxPage;
            }

            return new ResponseEntity<>(educationalInstitutionService.findAll(query, page, limit, sortBy), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(educationalInstitutionService.findAll(query, sortBy), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Crea una institución educativa", authorizations = {@Authorization(value = "apiKey") })
    @PostMapping(value = "/educational_institution")
    public ResponseEntity<?> saveEducationalInstitution(@RequestBody EducationalInstitution data, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        data.setStatus(true);
        EducationalInstitution result = educationalInstitutionService.insert(data);


        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualiza una institución educativa", authorizations = { @Authorization(value = "apiKey")})
    @PutMapping(value = "/educational_institution")
    public ResponseEntity<?> updateEducationalInstitution (@RequestBody EducationalInstitution data, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        EducationalInstitution dataInDB = educationalInstitutionService.findById(data.getIdEducationalInstitution());
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe institución educativa con código: " + data.getIdEducationalInstitution());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            data.setStatus(true);
            educationalInstitutionService.update(data);
            result.put("success", true);
            result.put("message", "Se ha actualizado los datos del registro.");
            result.put("result", data);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina una institución educativa", authorizations = { @Authorization(value = "apiKey")})
    @DeleteMapping(value = "/educational_institution/{idEducationalInstitution}")
    public ResponseEntity<?> deleteEducationalInstitution (@PathVariable(value = "idEducationalInstitution") Integer idEducationalInstitution, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        EducationalInstitution dataInDB = educationalInstitutionService.findById(idEducationalInstitution);
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe institución educativas con código: " + idEducationalInstitution);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            dataInDB.setStatus(false);
            educationalInstitutionService.delete(dataInDB);
            result.put("success", true);
            result.put("message", "Se ha eliminado los datos del registro.");
            result.put("result", dataInDB);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista directorio de instituciones educativas", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/educational_institution_directory")
    public ResponseEntity<?> findAllEducationalInstitutionDirectory(
            @RequestParam(value = "idEducationalInstitution", required = false, defaultValue = "-1") Integer idEducationalInstitution,
            @RequestParam(value = "position", required = false, defaultValue = "") String position,
            @RequestParam(value = "type", required = false, defaultValue = "grilla") String type,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "-1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
            @RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
            HttpServletRequest request) {

        if(type.equals("grilla")) {
            int maxPage = 10;

            if (page == -1 && limit == -1 && "".equals(sortBy)) {
                page = 1;
                limit = maxPage;
            }else if (limit != -1 && page == -1) {
                page = 1;
            } else if (page != -1 && limit == -1) {
                limit = maxPage;
            }

            return new ResponseEntity<>(educationalInstitutionDirectoryService.findAll(idEducationalInstitution, position, query, page, limit, sortBy), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(educationalInstitutionDirectoryService.findAll(idEducationalInstitution, position, query, sortBy), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Crea una nueva persona en directorio de institución educativa", authorizations = {@Authorization(value = "apiKey") })
    @PostMapping(value = "/educational_institution_directory")
    public ResponseEntity<?> saveEducationalInstitutionDirectory(@RequestBody EducationalInstitutionDirectory data, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        data.setStatus(true);
        EducationalInstitutionDirectory result = educationalInstitutionDirectoryService.insert(data);


        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualiza una persona en directorio de institución educativa", authorizations = { @Authorization(value = "apiKey")})
    @PutMapping(value = "/educational_institution_directory")
    public ResponseEntity<?> updateEducationalInstitutionDirectory (@RequestBody EducationalInstitutionDirectory data, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        EducationalInstitutionDirectory dataInDB = educationalInstitutionDirectoryService.findById(data.getIdEducationalInstitutionDirectory());
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe registro en directorio de institución educativa con código: " + data.getIdEducationalInstitutionDirectory());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            data.setStatus(true);
            educationalInstitutionDirectoryService.update(data);
            result.put("success", true);
            result.put("message", "Se ha actualizado los datos del registro.");
            result.put("result", data);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina persona de directorio de institución educativa", authorizations = { @Authorization(value = "apiKey")})
    @DeleteMapping(value = "/educational_institution_directory/{idEducationalInstitutionDirectory}")
    public ResponseEntity<?> deleteEducationalInstitutionDirectory (@PathVariable(value = "idEducationalInstitutionDirectory") Integer idEducationalInstitutionDirectory, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        EducationalInstitutionDirectory dataInDB = educationalInstitutionDirectoryService.findById(idEducationalInstitutionDirectory);
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe registro en directorio de institución educativa con código: " + idEducationalInstitutionDirectory);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            dataInDB.setStatus(false);
            educationalInstitutionDirectoryService.delete(dataInDB);
            result.put("success", true);
            result.put("message", "Se ha eliminado los datos del registro.");
            result.put("result", dataInDB);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista todas las instituciones", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/institution")
    public ResponseEntity<?> findAllInstitution(
            @RequestParam(value = "idTypeInstitution", required = false, defaultValue = "-1") Integer idTypeInstitution,
            @RequestParam(value = "type", required = false, defaultValue = "grilla") String type,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "-1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
            @RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
            HttpServletRequest request) {

        if(type.equals("grilla")) {
            int maxPage = 10;

            if (page == -1 && limit == -1 && "".equals(sortBy)) {
                page = 1;
                limit = maxPage;
            }else if (limit != -1 && page == -1) {
                page = 1;
            } else if (page != -1 && limit == -1) {
                limit = maxPage;
            }

            return new ResponseEntity<>(institutionService.findAll(idTypeInstitution, query, page, limit, sortBy), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(institutionService.findAll(idTypeInstitution, query, sortBy), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Crea una institución", authorizations = {@Authorization(value = "apiKey") })
    @PostMapping(value = "/institution")
    public ResponseEntity<?> saveInstitution(@RequestBody Institution data, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        data.setStatus(true);
        Institution result = institutionService.insert(data);


        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualiza una institución", authorizations = { @Authorization(value = "apiKey")})
    @PutMapping(value = "/institution")
    public ResponseEntity<?> updateInstitution (@RequestBody Institution data, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        Institution dataInDB = institutionService.findById(data.getIdInstitution());
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe institución con código: " + data.getIdInstitution());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            data.setStatus(true);
            institutionService.update(data);
            result.put("success", true);
            result.put("message", "Se ha actualizado los datos del registro.");
            result.put("result", data);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina una institución", authorizations = { @Authorization(value = "apiKey")})
    @DeleteMapping(value = "/institution/{idInstitution}")
    public ResponseEntity<?> deleteInstitution (@PathVariable(value = "idInstitution") Integer idInstitution, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        Institution dataInDB = institutionService.findById(idInstitution);
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe institución con código: " + idInstitution);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            dataInDB.setStatus(false);
            institutionService.delete(dataInDB);
            result.put("success", true);
            result.put("message", "Se ha eliminado los datos del registro.");
            result.put("result", dataInDB);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista directorio de instituciones aliadas", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/institution_directory")
    public ResponseEntity<?> findAllInstitutionDirectory(
            @RequestParam(value = "idInstitution", required = false, defaultValue = "-1") Integer idInstitution,
            @RequestParam(value = "type", required = false, defaultValue = "grilla") String type,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "-1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
            @RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
            HttpServletRequest request) {

        if(type.equals("grilla")) {
            int maxPage = 10;

            if (page == -1 && limit == -1 && "".equals(sortBy)) {
                page = 1;
                limit = maxPage;
            }else if (limit != -1 && page == -1) {
                page = 1;
            } else if (page != -1 && limit == -1) {
                limit = maxPage;
            }

            return new ResponseEntity<>(institutionDirectoryService.findAll(idInstitution, query, page, limit, sortBy), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(institutionDirectoryService.findAll(idInstitution, query, sortBy), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Crea una nueva persona en directorio de institución aliada", authorizations = {@Authorization(value = "apiKey") })
    @PostMapping(value = "/institution_directory")
    public ResponseEntity<?> saveInstitutionDirectory(@RequestBody InstitutionDirectory data, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        data.setStatus(true);
        InstitutionDirectory result = institutionDirectoryService.insert(data);


        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualiza una persona en directorio de institución aliada", authorizations = { @Authorization(value = "apiKey")})
    @PutMapping(value = "/institution_directory")
    public ResponseEntity<?> updateInstitutionDirectory (@RequestBody InstitutionDirectory data, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        InstitutionDirectory dataInDB = institutionDirectoryService.findById(data.getIdInstitutionDirectory());
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe registro en directorio de institución aliada con código: " + data.getIdInstitutionDirectory());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            data.setStatus(true);
            institutionDirectoryService.update(data);
            result.put("success", true);
            result.put("message", "Se ha actualizado los datos del registro.");
            result.put("result", data);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina persona de directorio de institución aliada", authorizations = { @Authorization(value = "apiKey")})
    @DeleteMapping(value = "/institution_directory/{idInstitutionDirectory}")
    public ResponseEntity<?> deleteInstitutionDirectory (@PathVariable(value = "idInstitutionDirectory") Integer idInstitutionDirectory, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        InstitutionDirectory dataInDB = institutionDirectoryService.findById(idInstitutionDirectory);
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe registro en directorio de institución aliada con código: " + idInstitutionDirectory);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            dataInDB.setStatus(false);
            institutionDirectoryService.delete(dataInDB);
            result.put("success", true);
            result.put("message", "Se ha eliminado los datos del registro.");
            result.put("result", dataInDB);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista todos los estudiantes aplicando filtros", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/student")
    public ResponseEntity<?> findAllStudent(
            @RequestParam(value = "idEducationalInstitution", required = false, defaultValue = "") Integer idEducationalInstitution,
            @RequestParam(value = "stateEvolution", required = false, defaultValue = "") String stateEvolution,
            @RequestParam(value = "sex", required = false, defaultValue = "") String sex,
            @RequestParam(value = "type", required = false, defaultValue = "grilla") String type,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "-1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "-1") Integer limit,
            @RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
            HttpServletRequest request) {

        if(type.equals("grilla")) {
            int maxPage = 10;

            if (page == -1 && limit == -1 && "".equals(sortBy)) {
                page = 1;
                limit = maxPage;
            }else if (limit != -1 && page == -1) {
                page = 1;
            } else if (page != -1 && limit == -1) {
                limit = maxPage;
            }

            return new ResponseEntity<>(studentService.findAll(idEducationalInstitution, stateEvolution, sex, query, page, limit, sortBy), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(studentService.findAll(idEducationalInstitution, stateEvolution, sex, query, sortBy), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Crea un estudiante", authorizations = {@Authorization(value = "apiKey") })
    @PostMapping(value = "/student")
    public ResponseEntity<?> saveStudent(@RequestBody Student data, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer idUser = 0;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            JwtUser userDetails = (JwtUser) auth.getPrincipal();
            idUser = userDetails.getId();

        } else {
            return new ResponseEntity<>(new RestException("No Autorizado"), HttpStatus.UNAUTHORIZED);
        }

        data.setIdUserReport(idUser);
        data.setRegistrationDate(Date.valueOf(LocalDate.now()));
        data.setStatus(true);
        Student result = studentService.insert(data);


        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualiza un estudiante", authorizations = { @Authorization(value = "apiKey")})
    @PutMapping(value = "/student")
    public ResponseEntity<?> updateStudent (@RequestBody Student data, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        Student dataInDB = studentService.findById(data.getIdStudent());
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe estudiante con código: " + data.getIdStudent());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            data.setStatus(true);
            studentService.update(data);
            result.put("success", true);
            result.put("message", "Se ha actualizado los datos del registro.");
            result.put("result", data);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina un estudiante", authorizations = { @Authorization(value = "apiKey")})
    @DeleteMapping(value = "/student/{idStudent}")
    public ResponseEntity<?> deleteStudent (@PathVariable(value = "idStudent") Integer idStudent, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        Student dataInDB = studentService.findById(idStudent);
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe estudiante con código: " + idStudent);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            dataInDB.setStatus(false);
            studentService.delete(dataInDB);
            result.put("success", true);
            result.put("message", "Se ha eliminado los datos del registro.");
            result.put("result", dataInDB);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista todos las incidencias", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/incidence")
    public ResponseEntity<?> findAllIncidence(
            @RequestParam(value = "idStudent", required = false, defaultValue = "") Integer idStudent,
            @RequestParam(value = "registrationDate", required = false, defaultValue = "") String registrationDate,
            @RequestParam(value = "type", required = false, defaultValue = "grilla") String type,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "-1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "-1") Integer limit,
            @RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
            HttpServletRequest request) {

        if(type.equals("grilla")) {
            int maxPage = 10;

            if (page == -1 && limit == -1 && "".equals(sortBy)) {
                page = 1;
                limit = maxPage;
            }else if (limit != -1 && page == -1) {
                page = 1;
            } else if (page != -1 && limit == -1) {
                limit = maxPage;
            }

            return new ResponseEntity<>(incidenceService.findAll(idStudent, registrationDate, query, page, limit, sortBy), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(incidenceService.findAll(idStudent, registrationDate, query, sortBy), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Crea una incidencia", authorizations = {@Authorization(value = "apiKey") })
    @PostMapping(value = "/incidence", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveIncidence(
            @RequestParam(value = "idStudent", required = false, defaultValue = "0") Integer idStudent,
            @RequestParam(value = "description", required = false, defaultValue = "") String description,
            @RequestParam(value = "titleEvidenceOne", required = false, defaultValue = "") String titleEvidenceOne,
            @RequestParam(value = "titleEvidenceTwo", required = false, defaultValue = "") String titleEvidenceTwo,
            @RequestParam(value = "type", required = false, defaultValue = "") String type,
            @RequestParam(value = "fileEvidenceOne", required = false) MultipartFile fileEvidenceOne,
            @RequestParam(value = "fileEvidenceTwo", required = false) MultipartFile fileEvidenceTwo,
            HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer idUser = 0;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            JwtUser userDetails = (JwtUser) auth.getPrincipal();
            idUser = userDetails.getId();
        } else {
            return new ResponseEntity<>(new RestException("No Autorizado"), HttpStatus.UNAUTHORIZED);
        }

        Incidence bean = new Incidence();
        bean.setIdStudent(idStudent);
        bean.setIdUser(idUser);
        bean.setDescription(description);
        bean.setType(type);
        bean.setRegistrationDate(Date.valueOf(LocalDate.now()));
        bean.setStatus(true);

        incidenceService.insert(bean);

        String pathRepo = parameterService.find(1).getValue();
        String folder = "/documents/incidences/";

        File folderFile = new File(pathRepo+folder);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }

        FileUtil fileUtil = new FileUtil();
        StringUtil stringUtil = new StringUtil();

        if (fileEvidenceOne != null) {
            String namefile = "INCIDENCE-" + idStudent + '-' + stringUtil.getAlphaNumeric(10);
            String pathLogo = fileUtil.upLoadFiles(pathRepo,folder,fileEvidenceOne,namefile);

            bean.setTitleEvidenceOne(titleEvidenceOne);
            bean.setEvidenceOne(pathLogo);
        }

        if (fileEvidenceTwo != null) {
            String namefile = "INCIDENCE-" + idStudent + '-' + stringUtil.getAlphaNumeric(10);
            String pathLogo = fileUtil.upLoadFiles(pathRepo,folder,fileEvidenceTwo,namefile);

            bean.setTitleEvidenceTwo(titleEvidenceOne);
            bean.setEvidenceTwo(pathLogo);
        }

        if (fileEvidenceOne != null || fileEvidenceTwo != null) {
            incidenceService.update(bean);
        }

        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", bean);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualiza una incidencia", authorizations = { @Authorization(value = "apiKey")})
    @PutMapping(value = "/incidence")
    public ResponseEntity<?> updateIncidence (@RequestBody Incidence data, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        Incidence dataInDB = incidenceService.findById(data.getIdIncidence());
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe incidencia con código: " + data.getIdStudent());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            data.setStatus(true);
            incidenceService.update(data);
            result.put("success", true);
            result.put("message", "Se ha actualizado los datos del registro.");
            result.put("result", data);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina una incidencia", authorizations = { @Authorization(value = "apiKey")})
    @DeleteMapping(value = "/incidence/{idIncidence}")
    public ResponseEntity<?> deleteIncidence (@PathVariable(value = "idIncidence") Integer idIncidence, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        Incidence dataInDB = incidenceService.findById(idIncidence);
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe incidencia con código: " + idIncidence);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            dataInDB.setStatus(false);
            incidenceService.delete(dataInDB);
            result.put("success", true);
            result.put("message", "Se ha eliminado los datos del registro.");
            result.put("result", dataInDB);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista todos los tratamientos", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/treatment")
    public ResponseEntity<?> findAllTreatment(
            @RequestParam(value = "idStudent", required = false, defaultValue = "") Integer idStudent,
            @RequestParam(value = "idSpecialist", required = false, defaultValue = "") Integer idSpecialist,
            @RequestParam(value = "registrationDate", required = false, defaultValue = "") String registrationDate,
            @RequestParam(value = "type", required = false, defaultValue = "grilla") String type,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "-1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "-1") Integer limit,
            @RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
            HttpServletRequest request) {

        if(type.equals("grilla")) {
            int maxPage = 10;

            if (page == -1 && limit == -1 && "".equals(sortBy)) {
                page = 1;
                limit = maxPage;
            }else if (limit != -1 && page == -1) {
                page = 1;
            } else if (page != -1 && limit == -1) {
                limit = maxPage;
            }

            return new ResponseEntity<>(treatmentService.findAll(idStudent, idSpecialist, registrationDate, query, page, limit, sortBy), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(treatmentService.findAll(idStudent, idSpecialist, registrationDate, query, sortBy), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Crea un tratamiento", authorizations = {@Authorization(value = "apiKey") })
    @PostMapping(value = "/treatment")
    public ResponseEntity<?> saveTreatment(@RequestBody Treatment data, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer idUser = 0;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            JwtUser userDetails = (JwtUser) auth.getPrincipal();
            idUser = userDetails.getId();

        } else {
            return new ResponseEntity<>(new RestException("No Autorizado"), HttpStatus.UNAUTHORIZED);
        }

        data.setIdSpecialist(idUser);
        data.setRegistrationDate(Date.valueOf(LocalDate.now()));
        data.setStatus(true);
        Treatment result = treatmentService.insert(data);


        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualiza un tratamiento", authorizations = { @Authorization(value = "apiKey")})
    @PutMapping(value = "/treatment")
    public ResponseEntity<?> updateTreatment (@RequestBody Treatment data, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        Treatment dataInDB = treatmentService.findById(data.getIdTreatment());
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe tratamiento con código: " + data.getIdStudent());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            data.setStatus(true);
            treatmentService.update(data);
            result.put("success", true);
            result.put("message", "Se ha actualizado los datos del registro.");
            result.put("result", data);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina un tratamiento", authorizations = { @Authorization(value = "apiKey")})
    @DeleteMapping(value = "/treatment/{idTreatment}")
    public ResponseEntity<?> deleteTreatment (@PathVariable(value = "idTreatment") Integer idTreatment, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        Treatment dataInDB = treatmentService.findById(idTreatment);
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe tratamiento con código: " + idTreatment);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            dataInDB.setStatus(false);
            treatmentService.delete(dataInDB);
            result.put("success", true);
            result.put("message", "Se ha eliminado los datos del registro.");
            result.put("result", dataInDB);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
