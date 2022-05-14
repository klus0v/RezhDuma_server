package com.example.rezh.services;


import com.example.rezh.entities.File;
import com.example.rezh.entities.Appeal;
import com.example.rezh.entities.News;
import com.example.rezh.entities.User;
import com.example.rezh.repositories.AppealRepository;
import com.example.rezh.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppealService {

    private final AppealRepository appealRepository;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    @Autowired
    private final FileStore fileStore;


    public List<Appeal> getFrequents() {
        return appealRepository.findAllByFrequent(true);
    }

    public List<Appeal> getAppeals(Long id, String token) throws AuthenticationException {
        if (!CheckUser(id, token))
            throw new AuthenticationException("Нет прав");
        return  appealRepository.findAllByUserId(id);
    }

    public List<Appeal> getAnsweredAppeals(Long id, String token, Boolean answered) throws AuthenticationException {
        if (!CheckUser(id, token))
            throw new AuthenticationException("Нет прав");
        return appealRepository.findAllByUserIdAndAnswered(id, answered);
    }

    public void deleteAppeal(Long id, String token, Long appealID) throws AuthenticationException {
        if (!CheckUser(id, token) || !Objects.equals(appealRepository.findById(appealID).get().getUser().getId(), id))
            throw new AuthenticationException("Нет прав");
        appealRepository.deleteById(appealID);
    }

    public void postAppeal(Long id, String token, String type, String district, String topic, String text, ArrayList<MultipartFile> files) throws IOException, AuthenticationException {

        if (!CheckUser(id, token))
            throw new AuthenticationException("Нет прав");

        User user = userRepository.getById(id);

        Appeal appeal = new Appeal();
        appeal.setType(type);
        appeal.setDistrictTag(district);
        appeal.setTopicTag(topic);
        appeal.setText(text);
        if (files != null)
            addFiles(files, appeal);
        appeal.setUser(user);
        user.addAppeal(appeal);

        userService.saveUser(user);
    }

    public void editAppeal(Long id, String token, Long appealID, String type, String district, String topic, String text, ArrayList<MultipartFile> files) throws IOException, AuthenticationException {

        if (!CheckUser(id, token) || !Objects.equals(appealRepository.findById(appealID).get().getUser().getId(), id))
            throw new AuthenticationException("Нет прав");

        Appeal appeal = appealRepository.getById(appealID);
        if (type != null)
            appeal.setType(type);
        if (district != null)
            appeal.setDistrictTag(district);
        if (topic != null)
            appeal.setTopicTag(topic);
        if (text != null)
            appeal.setText(text);
        if (files != null)
            addFiles(files, appeal);
        appealRepository.save(appeal);
    }

    private boolean CheckUser(Long id, String token) {
        var emailString = new String(Base64.getDecoder().decode(token.substring(42, 150)));
        var endIndex = emailString.indexOf(",") - 1;


        String email = userRepository.getById(id).getEmail();
        String tokenEmail = emailString.substring(8, endIndex);

        return email.equals(tokenEmail);
    }

    private void addFiles(ArrayList<MultipartFile> files, Appeal appeal) throws IOException {
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


                File appealFile = new File();
                appealFile.setFileName("https://storage.yandexcloud.net/rezh3545/" + filename);
                appealFile.setAppeal(appeal);
                appeal.addFile(appealFile);
            }
        }
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    public Appeal getAppeal(Long appealID) {
        return appealRepository.getById(appealID);
    }


    public List<Appeal> getAllAppeals() {
        return appealRepository.findAll();
    }

    public List<Appeal> getFiltredAllAppeals(String type, String topicTag, String districtTag) {

        if (type != null && topicTag != null && districtTag != null)
            return appealRepository.findAllByTypeAndTopicTagAndDistrictTag(type, topicTag, districtTag);

        if (type != null && topicTag != null)
            return appealRepository.findAllByTypeAndTopicTag(type, topicTag);
        if (type != null && districtTag != null)
            return appealRepository.findAllByTypeAndDistrictTag(type, districtTag);
        if (topicTag != null && districtTag != null)
            return appealRepository.findAllByTopicTagAndDistrictTag(topicTag, districtTag);

        if (type != null)
            return appealRepository.findAllByType(type);
        if (topicTag != null)
            return appealRepository.findAllByTopicTag(topicTag);
        if (districtTag != null)
            return appealRepository.findAllByDistrictTag(districtTag);

        return new ArrayList<Appeal>();
    }

    public void setAnswer(Long appealID, Long id, String response, Boolean frequent) {
            Appeal appeal = appealRepository.getById(appealID);
            if (response != null)
                appeal.setResponse(response);
            if (frequent)
                appeal.setFrequent(true);
            if (!appeal.getAnswered()) {
                appeal.setAnswered(true);
                appeal.setResponseDate(LocalDateTime.now());
            }
            if (userRepository.findById(id).isPresent()) {
                User responsible = userRepository.getById(id);
                appeal.setResponsibleName(responsible.getFirstName() + " " + responsible.getLastName());
            }

    }

    public List<Appeal> doPagination(List<Appeal> appeals, Integer page, Integer count) {
        List<Appeal> currentAppeals = new ArrayList<>();
        for (int i = (page-1)*count; i < page*count; i++) {
            if (i > appeals.size() - 1)
                break;
            currentAppeals.add(appeals.get(i));
        }
        return currentAppeals;
    }
}
