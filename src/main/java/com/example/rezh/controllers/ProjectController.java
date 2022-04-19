package com.example.rezh.controllers;

import com.example.rezh.entities.ProjectEntity;
import com.example.rezh.exceptions.ProjectNotFoundException;
import com.example.rezh.models.Project;
import com.example.rezh.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @GetMapping(params = {"id"})
    public ResponseEntity getOneProject(@RequestParam Integer id) {
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
    public ResponseEntity postProject(@RequestBody ProjectEntity project) {
        try {
            projectService.postProject(project);
            return ResponseEntity.ok("Проект добавлен");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(params = {"id"})
    public ResponseEntity deleteProject(@RequestParam Integer id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok("Проект удален");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity editProject(@RequestBody ProjectEntity project, @PathVariable Integer id) {
        try {
            projectService.editProject(project, id);
            return ResponseEntity.ok("Проект изменен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}