package com.example.rezh.rest;


import com.example.rezh.entities.User;
import com.example.rezh.exceptions.UserNotFoundException;
import com.example.rezh.models.UserModel;
import com.example.rezh.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/edit/profile")
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

    @PatchMapping("/edit/password")
    public ResponseEntity edutUserPassword(@RequestHeader(name = "Authorization") String token,
                                           @RequestParam String password,
                                           @RequestParam String newPassword) {
        try {
            var userID = userService.GetUserId(token);
            userService.editPassword(userID,password, newPassword);
            return ResponseEntity.ok().body("Пароль успешно изменен");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}