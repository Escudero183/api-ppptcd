package gob.pe.devida.ppptcd.controller.v1;

import gob.pe.devida.ppptcd.config.exception.ApiPPPTCDException;
import gob.pe.devida.ppptcd.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * File created by Linygn Escudero$ on 11/11/23$
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/services", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ServicesController {

    private static final String ESCALE_SERVICE_URL = "https://escale.minedu.gob.pe/padron/rest/instituciones";
    public static final String pathApiCR = "https://dev.regionsanmartin.gob.pe/planificapp";

    @ApiOperation(value = "Busca en el Padrón de II.EE. en ESCALE", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping(value = "/escale")
    public ResponseEntity<?> findIIEEEscale (
            @RequestParam(value = "estado", required = false, defaultValue = "1") String estado,
            @RequestParam(value = "ubigeo", required = false, defaultValue = "") String ubigeo,
            @RequestParam(value = "nombreIE", required = false, defaultValue = "") String nombreIE,
            @RequestParam(value = "ugel", required = false, defaultValue = "") String ugel,
            @RequestParam(value = "codmod", required = false, defaultValue = "") String codmod,
            HttpServletRequest request) throws URISyntaxException {

        String params = "?&estados=" + estado + "&ubigeo=" + ubigeo + "&nombreIE=" + nombreIE + "&ugel=" + ugel + "&start=0&codmod=" + codmod + "&codlocal=&nombreCP=&disVrae=S&disJuntos=S&disCrecer=S&disNinguno=S&matIndigena=";
        params = params.trim().replace(" ", "+");
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(ESCALE_SERVICE_URL + params);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        if(result.getStatusCodeValue() == 200) {
            byte[] responseBody = result.getBody().getBytes();
            String normalizedResponse = new String(responseBody, StandardCharsets.UTF_8);
            return new ResponseEntity<>(normalizedResponse, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Ha ocurrido un error:" + ESCALE_SERVICE_URL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Obtiene datos de un ciudadano peruano a través de la Consulta Reniec", authorizations = {@Authorization(value = "apiKey") })
    @GetMapping("/reniec/getDatosCiudadano/{dni}")
    public ResponseEntity<?> ConsultaReniec(@PathVariable(value = "dni") String dni, HttpServletRequest request) throws URISyntaxException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String contextCR = "/api/v1/herramientas/";
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(pathApiCR + contextCR + dni);

            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

            if(result.getStatusCodeValue() == 200) {
                return new ResponseEntity<>(result.getBody().toString(), HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Ha ocurrido un error:" + pathApiCR + contextCR, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiPPPTCDException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
