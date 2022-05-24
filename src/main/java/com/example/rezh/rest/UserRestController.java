package com.example.rezh.rest;


import com.example.rezh.entities.Appeal;
import com.example.rezh.entities.User;
import com.example.rezh.exceptions.HistoryNotFoundException;
import com.example.rezh.exceptions.UserNotFoundException;
import com.example.rezh.models.AppealModel;
import com.example.rezh.models.HistoryModel;
import com.example.rezh.models.UserModel;
import com.example.rezh.services.AppealService;
import com.example.rezh.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserRestController {

    private final UserServiceImpl userService;

    @GetMapping("/profile")
    public ResponseEntity getUserProfile(@RequestHeader(name = "Authorization") String token) {
        try {
            var userID = userService.GetUserId(token);
            return ResponseEntity.ok().body(UserModel.toModel(userService.getUser(userID)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping("/edit")
    public ResponseEntity edutUserProfile(@RequestHeader(name = "Authorization") String token,
                                          @RequestBody User user) {
        try {
            var userID = userService.GetUserId(token);
            userService.editUser(userID, user);
            return ResponseEntity.ok().body("Данные успешно измененны");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}