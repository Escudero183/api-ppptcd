package gob.pe.devida.ppptcd.config.security.service;

import gob.pe.devida.ppptcd.config.security.token.JwtTokenUtil;
import gob.pe.devida.ppptcd.model.User;
import gob.pe.devida.ppptcd.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */
@Service
public class SecurityService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil tokenUtil;

    public boolean hasAccess(String solicitud, String accion, HttpServletRequest request)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException,
            IllegalArgumentException, UnsupportedEncodingException {
        String requestHeader = request.getHeader("Authorization");
        String token = requestHeader.substring(7);
        String username = tokenUtil.getUsernameFromToken(token);
        User usuario = userRepository.findByLogin(username);
        return true;
    }
}
