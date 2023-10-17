package gob.pe.devida.ppptcd.controller.v1;

import gob.pe.devida.ppptcd.model.EducationalInstitution;
import gob.pe.devida.ppptcd.model.TypeInstitution;
import gob.pe.devida.ppptcd.service.EducationalInstitutionService;
import gob.pe.devida.ppptcd.service.TypeInstitutionService;
import io.swagger.annotations.ApiOperation;
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
    private EducationalInstitutionService educationalInstitutionService;

    @ApiOperation(value = "Lista todos los tipos de instituciones"/*, authorizations = {@Authorization(value = "apiKey") }*/)
    @GetMapping(value = "/type_institution")
    public ResponseEntity<?> findAllTypeInstitution() {
        return new ResponseEntity<>(typeInstitutionService.findAll(), HttpStatus.OK);
    }

    ;

    @ApiOperation(value = "Crea un tipo de institución"/*, authorizations = {@Authorization(value = "apiKey") }*/)
    @PostMapping(value = "/type_institution")
    public ResponseEntity<?> saveTypeInstitution(@RequestBody TypeInstitution data, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        TypeInstitution result = typeInstitutionService.insert(data);


        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista todos las instituciones educativas"/*, authorizations = {@Authorization(value = "apiKey") }*/)
    @GetMapping(value = "/educational_institution")
    public ResponseEntity<?> findAllEducationalInstitution() {
        return new ResponseEntity<>(educationalInstitutionService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Crea una institución educativa"/*, authorizations = {@Authorization(value = "apiKey") }*/)
    @PostMapping(value = "/educational_institution")
    public ResponseEntity<?> saveEducationalInstitution(@RequestBody EducationalInstitution data, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        EducationalInstitution result = educationalInstitutionService.insert(data);


        response.put("success", true);
        response.put("message", "Se ha registrado correctamente.");
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
