package gob.pe.devida.ppptcd.service;

import gob.pe.devida.ppptcd.model.User;
import gob.pe.devida.ppptcd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * File created by Linygn Escudero$ on 16/10/2023$
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User insert(User item) {
        return userRepository.save(item);
    }

    public void update(User item) {
        userRepository.save(item);
    }

    public void delete (User item) {
        userRepository.delete(item);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findById(Integer idUser) {
        return userRepository.findById(idUser).orElse(null);
    }

    public void resetPassword(Integer idUser, String password) {
        userRepository.resetPassword(idUser, password);
    }
}
