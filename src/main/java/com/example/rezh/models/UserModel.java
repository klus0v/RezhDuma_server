package com.example.rezh.models;


import com.example.rezh.entities.Role;
import com.example.rezh.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Integer phone;
    private String firstName;
    private String lastName;
    private String patronymic;
    private List<String> roles;


    public static UserModel toModel(User userEntity) {
        UserModel user = new UserModel();

        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setPatronymic(userEntity.getPatronymic());
        List<String> roles = new ArrayList<String>();
        for (Role role : userEntity.getRoles()) {
            roles.add(role.getName());
        }
        user.setRoles(roles);

        return user;
    }

    public static List<UserModel> toModel(List<User> userEntities) {
        List<UserModel> userModels = new ArrayList<>();
        for (User userEntity: userEntities ) {
            userModels.add(toModel(userEntity));
        }
        return userModels;
    }

}

