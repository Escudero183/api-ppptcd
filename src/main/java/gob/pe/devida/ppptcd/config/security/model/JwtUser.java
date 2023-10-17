package gob.pe.devida.ppptcd.config.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * File created by Linygn Escudero$ on 17/10/2023$
 */
public class JwtUser implements UserDetails {
    private static final long serialVersionUID = 1L;
    private final Integer id;
    private final String username;
    private final String email;

    private final String login;
    private String password;

    private final String display;
    private final Collection<? extends GrantedAuthority> authorities;


    public JwtUser(Integer id, String username, String email, String login, String password, String display, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.login = login;
        this.password = password;
        this.display = display;
        this.authorities = authorities;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getDisplay() {
        return display;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
