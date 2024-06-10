package com.cine.back.board.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cine.back.config.file.S3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Uploader s3Uploader;

    public String uploadFile(MultipartFile file, String directory) throws IOException {
        return s3Uploader.upload(file, directory);
    }

    public void deleteFile(String fileUrl) throws IOException {
        s3Uploader.deleteFile(fileUrl);
    }
}
