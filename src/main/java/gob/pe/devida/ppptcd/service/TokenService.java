package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.Token;
import gob.pe.devida.ppptcd.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */
@Service
@Transactional
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public void insert(Token token) {
        tokenRepository.save(token);
    }

    public boolean checkTokenByUser(String token, Integer idUser, String userAgent) {
        Token tokenBean = tokenRepository.findByHashAndUser(token, idUser, userAgent);
        return tokenBean != null;
    }

    public Token findTokenByUser(String token, Integer idUser, String userAgent) {
        return tokenRepository.findByHashAndUser(token, idUser, userAgent);
    }

    public void delete(Token tokenBean) {
        tokenRepository.delete(tokenBean);
    }
}
