package com.example.rezh.services;


import com.example.rezh.email.EmailSender;
import com.example.rezh.entities.Role;

import com.example.rezh.entities.User;
import com.example.rezh.registration.token.ConfirmationToken;
import com.example.rezh.registration.token.ConfirmationTokenService;
import com.example.rezh.repositories.RoleRepository;
import com.example.rezh.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            log.error("User {} not found in the database", email);
            throw new UsernameNotFoundException("User not found in the database");
        } else
            log.info("User found in the database: {}", user);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));});

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getEnable(), true, true, true, authorities);
    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null)
            return false;
        log.info("Save new user {} to the database", user.getFirstName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        String link = "http://192.168.0.103:8080/api/registration/confirm?token=" + token;
        emailSender.send(
                user.getEmail(), user.getFirstName() + ", для подтверждения почты, передите поссылке: " + link);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return true;
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("A new role {} to user {}", roleName, email);
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String email) {
        log.info("Fetching user {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all user");
        return userRepository.findAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
