package com.example.rezh.controllers;

import com.example.rezh.entities.ProjectEntity;
import com.example.rezh.exceptions.ProjectNotFoundException;
import com.example.rezh.models.Project;
import com.example.rezh.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @GetMapping(value = "{id}")
    public ResponseEntity getOneProject(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(Project.toModel(projectService.getOneProject(id)));
        } catch (ProjectNotFoundException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getAllProjects() {
        try {
            return ResponseEntity.ok(Project.toModel(projectService.getAllProjects()));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity postProject(@RequestParam(required = false) String title,
                                      @RequestParam(required = false) String text,
                                      @RequestParam(required = false) MultipartFile files) {
        try {
            projectService.postProject(title, text, files);
            return ResponseEntity.ok("Проект добавлен");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteProject(@PathVariable Integer id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok("Проект удален");
        } catch (ProjectNotFoundException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity editProject(@RequestParam(required = false) String title,
                                      @RequestParam(required = false) String text,
                                      @RequestParam(required = false) MultipartFile files,
                                      @PathVariable Integer id) {
        try {
            projectService.editProject(title, text, files, id);
            return ResponseEntity.ok("Проект изменен");
        } catch (ProjectNotFoundException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}