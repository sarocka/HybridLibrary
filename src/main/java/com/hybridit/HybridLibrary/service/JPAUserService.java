package com.hybridit.HybridLibrary.service;

import com.hybridit.HybridLibrary.model.User;
import com.hybridit.HybridLibrary.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JPAUserService implements UserService {

    private final UserRepository userRepository;

    public JPAUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findOne(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id provided does not exist");
        }
        return userRepository.getOne(id);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users to display");
        }
        return users;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with provided id does not exist");
        }
        User deleted = userRepository.getOne(id);
        userRepository.deleteById(id);
        return deleted;
    }

    @Override
    public User update(User fromRequestBody, Long id) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(fromRequestBody.getUsername());
            user.setPassword(fromRequestBody.getPassword());
            user.setFirstname(fromRequestBody.getFirstname());
            user.setLastname(fromRequestBody.getLastname());
            user.setRole(fromRequestBody.getRole());
            userRepository.save(user);
            return user;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with provided id does not exist"));
    }

    @Override
    public User registerNewUser(User user) {
        if(userRepository.findByUsername(user.getUsername())==null){
            user.setPassword(user.getPassword());
            return userRepository.save(user);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
