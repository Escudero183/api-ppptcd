package gob.pe.devida.ppptcd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@SpringBootApplication
@EnableJpaAuditing
public class PpptcdApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PpptcdApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PpptcdApplication.class);
    }

}
