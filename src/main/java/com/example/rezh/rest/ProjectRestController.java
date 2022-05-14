package com.example.rezh.rest;

import com.example.rezh.exceptions.ProjectNotFoundException;
import com.example.rezh.filter.models.ProjectModel;
import com.example.rezh.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping("/api/projects")
public class ProjectRestController {

    @Autowired
    private ProjectService projectService;


    @GetMapping(value = "{id}")
    public ResponseEntity getOneProject(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(ProjectModel.toModel(projectService.getOneProject(id)));
        } catch (ProjectNotFoundException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getAllProjects(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer count) {
        try {
            if (page == null || count == null)
                return ResponseEntity.ok().body(ProjectModel.toModel(projectService.getAllProjects()));
            return ResponseEntity.ok(ProjectModel.toModel(projectService.getProjectsPagination(page, count)));
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping
    public ResponseEntity postProject(@RequestParam(required = false) String title,
                                      @RequestParam(required = false) String text,
                                      @RequestParam(required = false) ArrayList<MultipartFile> files) {
        try {
            projectService.postProject(title, text, files);
            return ResponseEntity.ok("Проект добавлен");
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok("Проект " + id + " удален");
        } catch (ProjectNotFoundException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e ) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity editProject(@RequestParam(required = false) String title,
                                      @RequestParam(required = false) String text,
                                      @RequestParam(required = false) ArrayList<MultipartFile> files,
                                      @PathVariable Long id) {
        try {
            projectService.editProject(title, text, files, id);
            return ResponseEntity.ok("Проект " + id + " изменен");
        } catch (ProjectNotFoundException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}