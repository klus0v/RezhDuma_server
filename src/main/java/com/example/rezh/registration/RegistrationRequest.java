package com.example.rezh.registration;


import lombok.*;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String email;
    private final String password;
    private final Integer phone;
    private final String firstName;
    private final String lastName;
    private final String patronymic;
}
