package com.example.rezh.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    @Value("${secret.key}")
    private String secretKey;

    @Value("${token.start}")
    private String tokenStart;

    private final AppealRepository appealRepository;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    @Autowired
    private final FileStore fileStore;


    public List<Appeal> getFrequents(String find, String type, String district, String topic) {
        return appealRepository.findTopAppeals("%" + find + "%", "%" + find + "%", "%" + type + "%", "%" + district + "%", "%" + topic + "%");
    }

    public List<Appeal> getAppeals(Long id, String token, Boolean answered, String find, String type, String district, String topic) throws AuthenticationException {
        if (!CheckUser(id, token))
            throw new AuthenticationException("Нет прав");
        if (answered != null)
            return  appealRepository.findUserAppealsAnswered(id, "%" + find + "%", answered, "%" + type + "%", "%" + district + "%", "%" + topic + "%");
        return  appealRepository.findUserAppeals(id,"%" + find + "%", "%" + type + "%", "%" + district + "%", "%" + topic + "%");
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




    public Appeal getAppeal(Long appealID) {
        return appealRepository.getById(appealID);
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
            appeal.setResponsibleName(responsible.getLastName() + responsible.getFirstName());
        }

    }

    public List<Appeal> getFiltredAllAppeals(Boolean answered, String find, String type, String topic, String district) {
        if (answered != null)
            return  appealRepository.findAllAppealsAnswered("%" + find + "%", answered,"%" + type + "%", "%" + district + "%", "%" + topic + "%");
        return  appealRepository.findAllAppeals("%" + find + "%", "%" + type + "%", "%" + district + "%", "%" + topic + "%");
    }




    private boolean CheckUser(Long id, String tokenString) {
        String token = tokenString.substring(tokenStart.length());
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String tokenEmail = decodedJWT.getSubject();




        String email = userRepository.getById(id).getEmail();


        return email.equals(tokenEmail);
    }




    private void addFiles(ArrayList<MultipartFile> files, Appeal appeal) {
        for (MultipartFile file : files) {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                String path = "rezh3545";
                String filename = "uploads/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
                fileStore.save(path, filename, file);

                File appealFile = new File();
                appealFile.setFileName("https://storage.yandexcloud.net/rezh3545/" + filename);
                appealFile.setAppeal(appeal);
                appeal.addFile(appealFile);
            }
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
