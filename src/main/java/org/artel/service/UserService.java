package org.artel.service;

import lombok.RequiredArgsConstructor;
import org.artel.entity.User;
import org.artel.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
//        Optional<User> byUsernameOrEmail = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        Optional<User> byUsernameOrEmail = userRepository.findByUsername(user.getUsername());
        if (byUsernameOrEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Такой пользователь уже есть");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

    public boolean signIn(User user) {
/*        User user1 = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "")); */
        User user1 = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return bCryptPasswordEncoder.matches(user.getPassword(), user1.getPassword());
    }
}