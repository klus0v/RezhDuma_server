package com.example.rezh.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.rezh.email.EmailSender;
import com.example.rezh.entities.Role;

import com.example.rezh.entities.User;
import com.example.rezh.exceptions.HistoryNotFoundException;
import com.example.rezh.exceptions.UserNotFoundException;
import com.example.rezh.registration.token.ConfirmationToken;
import com.example.rezh.registration.token.ConfirmationTokenService;
import com.example.rezh.repositories.RoleRepository;
import com.example.rezh.repositories.UserRepository;
import com.example.rezh.rest.UserRestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Value("${confirm.link}")
    private String confirmLink;

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

        confirmEmail(user);

        return true;
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


    public User getUser(Long id) throws UserNotFoundException {
        if (id == null || userRepository.findById(id).isEmpty())
            throw new UserNotFoundException("Юзер не найден");
        return userRepository.getById(id);
    }

    public void editUser(Long userID, User user)  throws UserNotFoundException {

        if (userID == null || userRepository.findById(userID).isEmpty())
            throw new UserNotFoundException("Юзер не найден");

        User currentUser = userRepository.getById(userID);
        if (!user.getFirstName().isEmpty())
            currentUser.setFirstName(user.getFirstName());
        if (!user.getLastName().isEmpty())
            currentUser.setLastName(user.getLastName());
        if (!user.getPatronymic().isEmpty())
            currentUser.setPatronymic(user.getPatronymic());
        if (!user.getPhone().isEmpty())
            currentUser.setPhone(user.getPhone());

        if (!user.getEmail().isEmpty() && !Objects.equals(user.getEmail(), currentUser.getEmail())) {
            currentUser.setEmail(user.getEmail());
            currentUser.setEnable(false);
            confirmEmail(currentUser);
        }

        userRepository.save(currentUser);
    }

    public void editPassword(Long userID, String password, String newPassword) throws UserNotFoundException {

        if (userID == null || userRepository.findById(userID).isEmpty())
            throw new UserNotFoundException("Юзер не найден");


        User user = userRepository.getById(userID);



        if (passwordEncoder.matches(password, user.getPassword()))
            user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }


    public Long GetUserId(String tokenString) {

        if (tokenString.isEmpty())
            return null;

        String token = tokenString.substring("Rezh ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String tokenEmail = decodedJWT.getSubject();

        if (userRepository.findByEmail(tokenEmail) == null)
            throw new UsernameNotFoundException("Пользователь не найден");

        User user = userRepository.findByEmail(tokenEmail);

        return user.getId();
    }

    private void confirmEmail(User user) {
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        String link = confirmLink + token;
        emailSender.send(
                user.getEmail(), user.getFirstName() + ", для подтверждения почты, передите поссылке: " + link);

        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }
}
