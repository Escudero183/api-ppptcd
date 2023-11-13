package gob.pe.devida.ppptcd.controller.v1;

import gob.pe.devida.ppptcd.config.exception.ApiPPPTCDException;
import gob.pe.devida.ppptcd.model.TypeInstitution;
import gob.pe.devida.ppptcd.model.TypeRiskPlace;
import gob.pe.devida.ppptcd.repository.UbigeoRepository;
import gob.pe.devida.ppptcd.service.TypeInstitutionService;
import gob.pe.devida.ppptcd.service.TypeRiskPlaceService;
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
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/setting", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SettingController {

    @Autowired
    private TypeInstitutionService typeInstitutionService;

    @Autowired
    private TypeRiskPlaceService typeRiskPlaceService;

    @Autowired
    private UbigeoRepository ubigeoRepository;

    @ApiOperation(value = "Lista todos los tipos de instituciones", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/type_institution")
    public ResponseEntity<?> findAllTypeInstitution(
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

            return new ResponseEntity<>(typeInstitutionService.findAll(query, page, limit, sortBy), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(typeInstitutionService.findAll(query, sortBy), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Crea un tipo de institución", authorizations = {@Authorization(value = "apiKey") })
    @PostMapping(value = "/type_institution")
    public ResponseEntity<?> saveTypeInstitution(@RequestBody TypeInstitution data, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        data.setStatus(true);
        TypeInstitution result = typeInstitutionService.insert(data);


        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualiza un tipo de institución", authorizations = { @Authorization(value = "apiKey")})
    @PutMapping(value = "/type_institution")
    public ResponseEntity<?> updateTypeInstitution (@RequestBody TypeInstitution data, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        TypeInstitution dataInDB = typeInstitutionService.findById(data.getIdTypeInstitution());
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe tipo de institución con código: " + data.getIdTypeInstitution());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            data.setStatus(true);
            typeInstitutionService.update(data);
            result.put("success", true);
            result.put("message", "Se ha actualizado los datos del registro.");
            result.put("result", data);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina una tipo de institución", authorizations = { @Authorization(value = "apiKey")})
    @DeleteMapping(value = "/type_institution/{idTypeInstitution}")
    public ResponseEntity<?> deleteTypeInstitution (@PathVariable(value = "idTypeInstitution") Integer idTypeInstitution, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        TypeInstitution dataInDB = typeInstitutionService.findById(idTypeInstitution);
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe tipo de institución con código: " + idTypeInstitution);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            dataInDB.setStatus(false);
            typeInstitutionService.delete(dataInDB);
            result.put("success", true);
            result.put("message", "Se ha eliminado los datos del registro.");
            result.put("result", dataInDB);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista todos los tipos de lugares de riesgo", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/type_risk_place")
    public ResponseEntity<?> findAllTypeRiskPlace(
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

            return new ResponseEntity<>(typeRiskPlaceService.findAll(query, page, limit, sortBy), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(typeRiskPlaceService.findAll(query, sortBy), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Crea un tipo de lugar de riesgo", authorizations = {@Authorization(value = "apiKey") })
    @PostMapping(value = "/type_risk_place")
    public ResponseEntity<?> saveTypeRiskPlace(@RequestBody TypeRiskPlace data, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        data.setStatus(true);
        TypeRiskPlace result = typeRiskPlaceService.insert(data);


        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualiza un tipo de lugar de riesgo", authorizations = { @Authorization(value = "apiKey")})
    @PutMapping(value = "/type_risk_place")
    public ResponseEntity<?> updateTypeRiskPlace (@RequestBody TypeRiskPlace data, HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        TypeRiskPlace dataInDB = typeRiskPlaceService.findById(data.getIdTypeRiskPlace());
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe tipo de lugar de riesgo con código: " + data.getIdTypeRiskPlace());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            data.setStatus(true);
            typeRiskPlaceService.update(data);
            result.put("success", true);
            result.put("message", "Se ha actualizado los datos del registro.");
            result.put("result", data);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Elimina un tipo de lugar de riesgo", authorizations = { @Authorization(value = "apiKey")})
    @DeleteMapping(value = "/type_risk_place/{idTypeRiskPlace}")
    public ResponseEntity<?> deleteTypeRiskPlace (@PathVariable(value = "idTypeRiskPlace") Integer idTypeRiskPlace, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        TypeRiskPlace dataInDB = typeRiskPlaceService.findById(idTypeRiskPlace);
        if(dataInDB == null) {
            result.put("success", false);
            result.put("message", "No existe tipo de lugar de riesgo con código: " + idTypeRiskPlace);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            dataInDB.setStatus(false);
            typeRiskPlaceService.delete(dataInDB);
            result.put("success", true);
            result.put("message", "Se ha eliminado los datos del registro.");
            result.put("result", dataInDB);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista Ubigeos", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/ubigeo")
    public ResponseEntity<?> findUbigeos(
            @RequestParam(value = "get", required = false, defaultValue = "dpto") String get,
            @RequestParam(value = "codUbigeo", required = false, defaultValue = "") String codUbigeo,
            HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();

        if(!get.equals("dpto") && !get.equals("prov") && !get.equals("dist") && !get.equals("all")) {
            result.put("success", false);
            result.put("message", "El parámetro get puede recibir: dpto, prov o dist");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        if(get.equals("prov")) {
            if(codUbigeo.length() == 2) {
                return new ResponseEntity<>(ubigeoRepository.findProvs(codUbigeo), HttpStatus.OK);
            }else {
                result.put("success", false);
                result.put("message", "El codUbigeo debe contener sólo dos dígitos, pertenecientes al Departamento");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }

        if(get.equals("dist")) {
            if(codUbigeo.length() == 4) {
                return new ResponseEntity<>(ubigeoRepository.findDists(codUbigeo), HttpStatus.OK);
            }else {
                result.put("success", false);
                result.put("message", "El codUbigeo debe contener sólo cuatro dígitos, pertenecientes a la Provincia");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }

        if(get.equals("all")) {
            if(codUbigeo.length() == 6) {
                return new ResponseEntity<>(ubigeoRepository.getUbigeoFull(codUbigeo), HttpStatus.OK);
            }else {
                result.put("success", false);
                result.put("message", "El codUbigeo debe contener 4 dígitos");
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(ubigeoRepository.findDptos(), HttpStatus.OK);
    }
}
