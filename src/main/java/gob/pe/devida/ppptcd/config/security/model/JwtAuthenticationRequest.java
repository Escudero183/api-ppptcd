package gob.pe.devida.ppptcd.config.security.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */
@ApiModel("Modelo de autentificación")
public final class JwtAuthenticationRequest {
    @ApiModelProperty(value = "Nombre de usuario", required = true)
    private String username;
    @ApiModelProperty(value = "Contraseña de usuario", required = true)
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
