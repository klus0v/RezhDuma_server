package com.example.rezh.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private Integer phone;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    private String firstName;

    private String lastName;

    private String patronymic;

    private Boolean enable = false;

    private Long tgId;

    public User(String email, String password, Integer phone, String firstName, String lastName, String patronymic) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }
}
