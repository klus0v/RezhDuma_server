package com.example.rezh.services;

import com.example.rezh.entities.File;
import com.example.rezh.entities.Project;
import com.example.rezh.exceptions.ProjectNotFoundException;
import com.example.rezh.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final FileStore fileStore;


    @Autowired
    public ProjectService(ProjectRepository projectRepository, FileStore fileStore) {
        this.projectRepository = projectRepository;
        this.fileStore = fileStore;
    }

    public Project getOneProject(Long id) throws ProjectNotFoundException {
        if (projectRepository.findById(id).isEmpty())
            throw new ProjectNotFoundException("Проект не найден");

        return projectRepository.getById(id);
    }

    public List<Project> getProjectsPagination(Integer page, Integer count) {
        List<Project> projects = getAllProjects();
        List<Project> currentProjects = new ArrayList<>();
        for (int i = (page-1)*count; i < page*count; i++) {
            if (i > projects.size() - 1)
                break;
            currentProjects.add(projects.get(i));
        }
        return currentProjects;
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public void postProject(String title, String text, ArrayList<MultipartFile> files) throws IOException {
        Project project = new Project();
        project.setTitle(title);
        project.setText(text);
        if (files != null)
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
                Map<String, String> metadata = extractMetadata(file);
                String path = "rezh3545";
                String filename = "uploads/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

                try {
                    fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }


                File projectFile = new File();
                projectFile.setFileName("https://storage.yandexcloud.net/rezh3545/" + filename);
                projectFile.setProject(project);
                project.addFile(projectFile);
            }
        }
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }
}
