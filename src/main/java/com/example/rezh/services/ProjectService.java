package com.example.rezh.services;

import com.example.rezh.entities.ProjectEntity;
import com.example.rezh.exceptions.ProjectNotFoundException;
import com.example.rezh.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public ProjectEntity getOneProject(Integer id) throws ProjectNotFoundException {
        ProjectEntity project = projectRepository.findById(id).get();
        if (project == null){
            throw new ProjectNotFoundException("Проект не найден");
        }
        return project;
    }

    public Iterable<ProjectEntity> getAllProjects(){
        return projectRepository.findAll();
    }

    public ProjectEntity postProject(ProjectEntity project)  {
        return projectRepository.save(project);
    }

    public Integer deleteProject(Integer id) {
        projectRepository.deleteById(id);
        return id;
    }

    public void editProject(ProjectEntity newProject, Integer id) throws ProjectNotFoundException {
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
