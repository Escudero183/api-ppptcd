package gob.pe.devida.ppptcd.controller.v1;

import gob.pe.devida.ppptcd.service.ParameterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * File created by Linygn Escudero$ on 20/12/23$
 */
@CrossOrigin("*")
@Controller
public class PDFController {

    @Autowired
    private ParameterService parameterService;

    @ApiOperation(value = "Muestra archivos pdf"/*, authorizations = {@Authorization(value = "apiKey") }*/)
    @GetMapping("/api/v1/pdf/show")
    public ResponseEntity<Resource> showPdf(@RequestParam String filePath) throws IOException {

        String fileDirectory = parameterService.find(1).getValue();
        String fullPath = fileDirectory + filePath;

        Path path = Paths.get(fullPath);
        Resource resource = new UrlResource(path.toUri());

        if (resource.exists() && resource.isReadable()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + path.getFileName().toString());
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
