package com.example.rezh.services;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;


@Service
public class FileStore {

    private final AmazonS3 s3;

    @Autowired
    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save(String path, String fileName, MultipartFile inputFile) {


        try {
            final ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentType(inputFile.getContentType());
            metaData.setContentLength(inputFile.getSize());

            s3.putObject(path, fileName, inputFile.getInputStream(), metaData);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to store file to s3", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}