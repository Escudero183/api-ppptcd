package gob.pe.devida.ppptcd.config.security.token;

import gob.pe.devida.ppptcd.config.security.model.JwtUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */
@Component
public class JwtTokenUtil {

    private final Clock clock = DefaultClock.INSTANCE;
    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "iat";
    private final String secret = "$Linygn183$*#";
    private Long expiration = 604800L;

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getUsernameFromToken(String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException,
            IllegalArgumentException, UnsupportedEncodingException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails, String userAgent) throws UnsupportedEncodingException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userAgent", userAgent);
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public Date getExpirationDateFromToken(String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Date getIssuedAtDateFromToken(String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Boolean validateToken(String token, UserDetails userDetails)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException,
            IllegalArgumentException, UnsupportedEncodingException {
        JwtUser user = (JwtUser) userDetails;
        // obtenemos el usuario del token
        final String username = getUsernameFromToken(token);
        // final Date created = getIssuedAtDateFromToken(token);
        // final Date expiration = getExpirationDateFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
        final Date fExpiration = getExpirationDateFromToken(token);
        return fExpiration.before(clock.now());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) throws UnsupportedEncodingException {
        Date createdDate = clock.now();
        Date expirationDate = calculeEspirationDate(createdDate);
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(createdDate)
                .setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, this.secret.getBytes("UTF-8"))
                .compact();
    }

    private Date calculeEspirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    private Claims getAllClaimsFromToken(String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException {
        return Jwts.parser().setSigningKey(this.secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();
    }

}
