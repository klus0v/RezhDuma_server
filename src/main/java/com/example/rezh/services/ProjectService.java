package com.example.rezh.services;

import com.example.rezh.entities.HistoryEntity;
import com.example.rezh.entities.ProjectEntity;
import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.exceptions.ProjectNotFoundException;
import com.example.rezh.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Value("${upload.path}")
    private String uploadPath;


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

    public ProjectEntity postProject(String title, String text, MultipartFile file) throws IOException {
        ProjectEntity project = new ProjectEntity();

        project.setTitle(title);
        project.setText(text);
        project.setFiles(addFile(file));

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

    public void editProject(String title, String text, MultipartFile file, Integer id) throws ProjectNotFoundException, IOException {
        var project = projectRepository.findById(id);
        if (project.isEmpty()){
            throw new ProjectNotFoundException("Проект не найден");
        }
        ProjectEntity currentProject = getOneProject(id);
        if (title != null)
            currentProject.setTitle(title);
        if (text != null)
            currentProject.setText(text);
        if (!file.isEmpty())
            currentProject.setFiles(addFile(file));
        projectRepository.save(currentProject);
    }



    private String addFile(MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDirectory = new File(uploadPath);
            if (!uploadDirectory.exists())
                uploadDirectory.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));
            return (uploadPath + "/" + resultFileName);
        }
        return null;
    }
}
