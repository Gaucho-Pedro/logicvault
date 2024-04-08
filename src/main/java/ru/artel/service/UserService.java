package ru.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.artel.entity.User;
import ru.artel.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found"));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User create(User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public User update(User newUser) {
        var user = findById(newUser.getId());

        if (newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }
        if (newUser.getPhoneNumber() != null) {
            user.setPhoneNumber(newUser.getPhoneNumber());
        }
        return userRepository.save(user);
    }

    public User changePassword(Long userId, String oldPassword, String newPassword) {
        var user = findById(userId);
        if (!signIn(user.getPassword(), oldPassword)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong old password");
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    public boolean signIn(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}