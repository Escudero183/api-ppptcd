package gob.pe.devida.ppptcd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String getIndex() {
        return "redirect:swagger-ui.html";
    }
}
