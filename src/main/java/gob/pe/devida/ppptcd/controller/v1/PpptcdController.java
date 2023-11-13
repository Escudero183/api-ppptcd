package gob.pe.devida.ppptcd.controller.v1;

import gob.pe.devida.ppptcd.config.exception.ApiPPPTCDException;
import gob.pe.devida.ppptcd.model.EducationalInstitution;
import gob.pe.devida.ppptcd.model.EducationalInstitutionDirectory;
import gob.pe.devida.ppptcd.model.Institution;
import gob.pe.devida.ppptcd.model.RiskPlace;
import gob.pe.devida.ppptcd.service.EducationalInstitutionDirectoryService;
import gob.pe.devida.ppptcd.service.EducationalInstitutionService;
import gob.pe.devida.ppptcd.service.InstitutionService;
import gob.pe.devida.ppptcd.service.RiskPlaceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

            return new ResponseEntity<>(educationalInstitutionDirectoryService.findAll(idEducationalInstitution, query, page, limit, sortBy), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(educationalInstitutionDirectoryService.findAll(idEducationalInstitution, query, sortBy), HttpStatus.OK);
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
}
