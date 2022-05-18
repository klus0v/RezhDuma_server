package com.example.rezh.registration;


import com.example.rezh.entities.User;
import com.example.rezh.registration.token.ConfirmationToken;
import com.example.rezh.registration.token.ConfirmationTokenService;
import com.example.rezh.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@AllArgsConstructor
public class RegistrationService {

    private final UserServiceImpl userService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(User user) {
        boolean isValidEmail = emailValidator.
                test(user.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        boolean info = userService.saveUser(user);
        if (!info)
            return "Пользователь с такой почтой уже есть";
        userService.addRoleToUser(user.getEmail(), "USER");

        return "Пользователь заргестрирован, для входа подтвердите почту";
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

