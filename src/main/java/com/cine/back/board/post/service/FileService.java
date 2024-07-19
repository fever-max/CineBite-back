package com.cine.back.board.post.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cine.back.config.file.S3Uploader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Uploader s3Uploader;

    public String uploadFile(MultipartFile file, String directory) throws IOException {
        log.info("# uploadFile 실행 - file: {} / directory: {}", file, directory);
        return s3Uploader.upload(file, directory);
    }

    public void deleteFile(String fileUrl) throws IOException {
        log.info("# deleteFile 실행 - fileUrl: {}", fileUrl);
        s3Uploader.deleteFile(fileUrl);
    }
}
