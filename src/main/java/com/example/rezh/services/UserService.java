package com.example.rezh.services;


import com.example.rezh.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    void addRoleToUser(String email, String roleName);
    User getUser(String email);
    List<User> getUsers();
}
