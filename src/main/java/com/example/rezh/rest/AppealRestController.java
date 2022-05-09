package com.example.rezh.rest;


import com.example.rezh.models.AppealModel;
import com.example.rezh.services.AppealService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/appeals")
@AllArgsConstructor
public class AppealRestController {

    private final AppealService appealService;

    //all
    @GetMapping("/popular")
    public ResponseEntity getFrequentAppeals() {
        try {
            return ResponseEntity.ok(AppealModel.toModel(appealService.getFrequents()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    //users
    @GetMapping(value = "/user/{id}")
    public ResponseEntity getUserAppeals(@PathVariable Long id,
                                         @RequestHeader(name = "Authorization") String token,
                                         @RequestParam(required = false) Boolean answered) {
        try {
            if (answered != null) {
                return ResponseEntity.ok().body(AppealModel.toModel(appealService.getAnsweredAppeals(id, token, answered)));
            }
            return ResponseEntity.ok().body(AppealModel.toModel(appealService.getAppeals(id, token)));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(value = "/user/{id}", params = {"appeal"})
    public ResponseEntity deleteUserAppeal(@PathVariable Long id,
                                           @RequestHeader(name = "Authorization") String token,
                                           @RequestParam Long appeal) {
        try {
            appealService.deleteAppeal(id, token, appeal);
            return ResponseEntity.ok("Обращение удалено");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping(value = "/user/{id}")
    public ResponseEntity postAppeal(@PathVariable Long id,
                                     @RequestHeader(name = "Authorization") String token,
                                     @RequestParam(required = false) String type,
                                     @RequestParam(required = false) String district,
                                     @RequestParam(required = false) String topic,
                                     @RequestParam(required = false) String text,
                                     @RequestParam(required = false) ArrayList<MultipartFile> files) {

        try {
            appealService.postAppeal(id, token, type, district, topic, text, files);
            return ResponseEntity.ok("Обращение создано");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PatchMapping(value = "/user/{id}", params = {"appeal"})
    public ResponseEntity editUserAppeal(@PathVariable Long id,
                                         @RequestHeader(name = "Authorization") String token,
                                         @RequestParam Long appeal,
                                         @RequestParam(required = false) String type,
                                         @RequestParam(required = false) String district,
                                         @RequestParam(required = false) String topic,
                                         @RequestParam(required = false) String text,
                                         @RequestParam(required = false) ArrayList<MultipartFile> files) {

        try {
            appealService.editAppeal(id, token, appeal, type, district, topic, text, files);
            return ResponseEntity.ok("Обращение изменено");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }



    //admins
    @GetMapping(value = "admin/{appealID}")
    public ResponseEntity getAnyOneAppeal(@PathVariable Long appealID) {
        try {
            return ResponseEntity.ok().body(AppealModel.toModel(appealService.getAppeal(appealID)));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/admin")
    public ResponseEntity getAllAppeals(@RequestParam(required = false) String type,
                                        @RequestParam(required = false) String topic,
                                        @RequestParam(required = false) String district) {
        try {
            if (type != null || topic != null || district != null)
                return ResponseEntity.ok().body(AppealModel.toModel(appealService.getFiltredAllAppeals(type, topic, district)));
            return ResponseEntity.ok().body(AppealModel.toModel(appealService.getAllAppeals()));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PatchMapping(value = "admin/{appealID}")
    public ResponseEntity setAnswerOnAppeal(@PathVariable Long appealID,
                                            @RequestParam(required = false) Long id,
                                            @RequestParam(required = false) String response,
                                            @RequestParam(required = false) Boolean frequent) {
        try {
            appealService.setAnswer(appealID, id, response, frequent);
            return ResponseEntity.ok("Ответ на обращение записан");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}