package com.example.rezh.services;

import com.example.rezh.entities.AllFiles;
import com.example.rezh.entities.Project;
import com.example.rezh.exceptions.NewsNotFoundException;
import com.example.rezh.exceptions.ProjectNotFoundException;
import com.example.rezh.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public Project getOneProject(Long id) throws ProjectNotFoundException {
        if (projectRepository.findById(id).isEmpty())
            throw new ProjectNotFoundException("Проект не найден");

        return projectRepository.getById(id);
    }

    public Iterable<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public void postProject(String title, String text, ArrayList<MultipartFile> files) throws IOException {
        Project project = new Project();
        project.setTitle(title);
        project.setText(text);
        addFiles(files, project);
        projectRepository.save(project);
    }

    public void deleteProject(Long id) throws ProjectNotFoundException {
        if (projectRepository.findById(id).isEmpty())
            throw new ProjectNotFoundException("Проект не найден");

        projectRepository.deleteById(id);
    }

    public void editProject(String title, String text, ArrayList<MultipartFile> files, Long id) throws ProjectNotFoundException, IOException {

        if (projectRepository.findById(id).isEmpty())
            throw new ProjectNotFoundException("Проект не найден");

        Project currentProject = getOneProject(id);
        if (title != null)
            currentProject.setTitle(title);
        if (text != null)
            currentProject.setText(text);
        if (files != null)
            addFiles(files, currentProject);

        projectRepository.save(currentProject);
    }

    private void addFiles(ArrayList<MultipartFile> files, Project project) throws IOException {

        for (MultipartFile file : files) {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFileName));

                AllFiles projectFile = new AllFiles();
                projectFile.setFileName(uploadPath + "/" + resultFileName);
                projectFile.setProjects(project);
                project.addFile(projectFile);
            }
        }
    }
}
