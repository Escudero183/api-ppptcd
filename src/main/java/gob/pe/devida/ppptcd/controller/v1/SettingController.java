package gob.pe.devida.ppptcd.controller.v1;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/setting", produces = { MediaType.APPLICATION_JSON_VALUE })
public class SettingController {

    @ApiOperation(value = "Lista todos tipos de instituciones")
    @GetMapping(value = "/type_institution")
    public ResponseEntity<?> findAllZona(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    };
}
