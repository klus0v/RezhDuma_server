package com.example.rezh.registration;


import com.example.rezh.entities.User;
import com.example.rezh.registration.token.ConfirmationToken;
import com.example.rezh.registration.token.ConfirmationTokenService;
import com.example.rezh.services.UserService;
import com.example.rezh.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserServiceImpl userService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    @Transactional
    public String register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (isValidEmail) {

            throw new IllegalStateException(request.getEmail() + "Email not valid");

        }

        String token = userService.SignUp(
                new User(
                        request.getEmail(),
                        request.getPassword(),
                        request.getPhone(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getPatronymic()
                ));
        userService.addRoleToUser(request.getEmail(), "ROLE_USER");

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }
}
