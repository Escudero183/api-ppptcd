package gob.pe.devida.ppptcd.config.security.service;

import gob.pe.devida.ppptcd.config.security.model.JwtUserFactory;
import gob.pe.devida.ppptcd.model.User;
import gob.pe.devida.ppptcd.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */
@Service
public class AuthService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null) {
            logger.error("No existe el Usuario.");
        }

        String nickname = username;
        User user = userRepository.findByLogin(nickname.toUpperCase());

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No existe el usuario '%s'.", username));
        } else {
            String perfil = "PERFIL"; // user.getIdProfile().toString();

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(perfil));
            return JwtUserFactory.create(user, authorities);
        }
    }
}
