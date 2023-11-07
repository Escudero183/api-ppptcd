package gob.pe.devida.ppptcd.controller.v1;

import gob.pe.devida.ppptcd.config.exception.ApiPPPTCDException;
import gob.pe.devida.ppptcd.config.exception.RestException;
import gob.pe.devida.ppptcd.config.security.model.JwtAuthenticationRequest;
import gob.pe.devida.ppptcd.config.security.model.JwtUser;
import gob.pe.devida.ppptcd.config.security.token.JwtTokenUtil;
import gob.pe.devida.ppptcd.model.Token;
import gob.pe.devida.ppptcd.model.User;
import gob.pe.devida.ppptcd.service.TokenService;
import gob.pe.devida.ppptcd.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Objects;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiResponses(value = { @ApiResponse(code = 200, message = "La solicitud ha tenido éxito"),
        @ApiResponse(code = 401, message = "El servidor no ha encontrado nada que coincida con el URI de solicitud") })
@Api(description = "Microservicio de autentificación")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService jwtUserDetailService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "*")
    @PostMapping("/auth")
    @ApiOperation(value = "Crea el Token, no es necesario estar autenticado")
    public ResponseEntity<?> createToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                         HttpServletRequest request) {
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            UserDetails userDetails = jwtUserDetailService.loadUserByUsername(authenticationRequest.getUsername());

            String token;
            if (checkUsuario(((JwtUser) userDetails).getId())) {
                JwtUser jwtUser = (JwtUser) userDetails;
                jwtTokenUtil.setExpiration(28800 * 1000L);
                String userAgent = request.getHeader("User-Agent");
                token = jwtTokenUtil.generateToken(userDetails, userAgent);

                User userBean = userService.findByLogin(authenticationRequest.getUsername().toUpperCase());
                try {
                    InetAddress remoteInetAddress = InetAddress.getByName(request.getRemoteAddr());
                    Token tokenBean = new Token();
                    tokenBean.setHostname(remoteInetAddress.getHostAddress());
                    tokenBean.setNombreEquipo(userAgent);
                    tokenBean.setUser(userBean);
                    tokenBean.setHash(token);

                    tokenService.insert(tokenBean);
                } catch (UnknownHostException e) {
                }

                HashMap<String, Object> result = new HashMap<String, Object>();
                result.put("tokenType", "Bearer");
                result.put("user", jwtUser);
                result.put("token", token);

                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new RestException("El usuario no tiene acceso"), HttpStatus.NOT_FOUND);
            }
        } catch (ApiPPPTCDException | UnsupportedEncodingException e) {
            return new ResponseEntity<>(new RestException(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Destruye el Token", authorizations = { @Authorization(value = "apiKey") })
    @DeleteMapping("/logout")
    public ResponseEntity<?> destroyToken(HttpServletRequest request) {
        String requestHeader = request.getHeader("Authorization");
        String token = requestHeader.substring(7);
        try {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            User usuario = userService.findByLogin(username);
            Token tokenBean = tokenService.findTokenByUser(token, usuario.getIdUser(),
                    request.getHeader("User-Agent"));
            tokenService.delete(tokenBean);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
                 | IllegalArgumentException | UnsupportedEncodingException e) {
            return new ResponseEntity<>(new RestException(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Verifica el Token", authorizations = { @Authorization(value = "apiKey") })
    @GetMapping("/check")
    public ResponseEntity<?> checkToken(HttpServletRequest request) {
        String requestHeader = request.getHeader("Authorization");
        String token = requestHeader.substring(7);
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            result.put("tokenValid", true);
            result.put("tokenUser", username);
            result.put("tokenExpired", new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a")
                    .format(jwtTokenUtil.getExpirationDateFromToken(token)));

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
                 | IllegalArgumentException | UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            result.put("token_valid", false);
            result.put("token_user", "");
            result.put("token_expired", "");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws ApiPPPTCDException {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new ApiPPPTCDException("User is disabled!");
        } catch (BadCredentialsException e) {
            throw new ApiPPPTCDException("Datos incorrectos!");
        }
    }

    private boolean checkUsuario(Integer idUser) {
        User user = userService.findById(idUser);
        if (user == null) {
            return false;
        } else {
            return user.isStatus();
        }
    }
}
