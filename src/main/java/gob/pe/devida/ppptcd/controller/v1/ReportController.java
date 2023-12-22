package gob.pe.devida.ppptcd.controller.v1;

import gob.pe.devida.ppptcd.model.RiskPlace;
import gob.pe.devida.ppptcd.repository.*;
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
 * File created by Linygn Escudero$ on 20/12/23$
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/report", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ReportController {

    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;

    @Autowired
    private InstitutionDirectoryRepository institutionDirectoryRepository;

    @Autowired
    private RiskPlaceRepository riskPlaceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    @ApiOperation(value = "Obtiene datos para el tablero de indicadores", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/main_board")
    public ResponseEntity<?> getMainBoard(HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();

        Integer countEI = educationalInstitutionRepository.countByStatusTrue();
        response.put("countEI", countEI);

        Integer countID = institutionDirectoryRepository.countByStatusTrue();
        response.put("countID", countID);

        Integer countRP = riskPlaceRepository.countByStatusTrue();
        response.put("countRP", countRP);

        Integer countS= studentRepository.countByStatusTrue();
        response.put("countS", countS);

        Integer countT= treatmentRepository.countByStatusTrue();
        response.put("countT", countS);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
