package com.example.rezh.services;

import com.example.rezh.entities.ProjectEntity;
import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.exceptions.ProjectNotFoundException;
import com.example.rezh.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public ProjectEntity getOneProject(Integer id) throws ProjectNotFoundException {
        var project = projectRepository.findById(id);
        if (project.isEmpty()){
            throw new ProjectNotFoundException("Проект не найден");
        }
        return project.get();
    }

    public Iterable<ProjectEntity> getAllProjects(){
        return projectRepository.findAll();
    }

    public ProjectEntity postProject(ProjectEntity project)  {
        return projectRepository.save(project);
    }

    public Integer deleteProject(Integer id) throws ProjectNotFoundException {
        var project = projectRepository.findById(id);
        if (project.isEmpty()){
            throw new ProjectNotFoundException("Проект не найден");
        }
        projectRepository.deleteById(id);
        return id;
    }

    public void editProject(ProjectEntity newProject, Integer id) throws ProjectNotFoundException {
        var project = projectRepository.findById(id);
        if (project.isEmpty()){
            throw new ProjectNotFoundException("Проект не найден");
        }
        ProjectEntity currentProject = getOneProject(id);
        if (newProject.getTitle() != null)
            currentProject.setTitle(newProject.getTitle());
        if (newProject.getText() != null)
            currentProject.setText(newProject.getText());
        if (newProject.getFiles() != null)
            currentProject.setFiles(newProject.getFiles());
        postProject(currentProject);
    }

}
