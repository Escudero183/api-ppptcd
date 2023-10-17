package gob.pe.devida.ppptcd.config.security.model;

import gob.pe.devida.ppptcd.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */
public final class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(User user, List<GrantedAuthority> authorities) {
        return new JwtUser(user.getIdUser(), user.getFirstName() + " " + user.getLastName(), user.getEmail(), user.getLogin(), user.getPassword(), user.getDisplay(), authorities);
    }
}
