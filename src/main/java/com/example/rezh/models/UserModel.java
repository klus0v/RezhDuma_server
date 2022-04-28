package com.example.rezh.models;


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


    public static UserModel toModel(User userEntity) {
        UserModel user = new UserModel();

        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setPatronymic(userEntity.getPatronymic());

        return user;
    }

    public static List<UserModel> toModel(List<User> userEntities) {
        List<UserModel> userModels = new ArrayList<UserModel>();
        for (User userEntity: userEntities ) {
            UserModel user = new UserModel();

            user.setId(userEntity.getId());
            user.setEmail(userEntity.getEmail());
            user.setPhone(userEntity.getPhone());
            user.setFirstName(userEntity.getFirstName());
            user.setLastName(userEntity.getLastName());
            user.setPatronymic(userEntity.getPatronymic());

            userModels.add(user);
        }
        return userModels;
    }

}

