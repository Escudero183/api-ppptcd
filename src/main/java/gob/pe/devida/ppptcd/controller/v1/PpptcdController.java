package gob.pe.devida.ppptcd.controller.v1;

import gob.pe.devida.ppptcd.config.exception.ApiPPPTCDException;
import gob.pe.devida.ppptcd.model.Institution;
import gob.pe.devida.ppptcd.model.RiskPlace;
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
}
