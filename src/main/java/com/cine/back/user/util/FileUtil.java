package com.cine.back.user.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Log4j2
@Component
@RequiredArgsConstructor
public class FileUtil {

    @Value("${file.upload-dir}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        File tempFolder = new File(uploadPath);
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
    }

    public String saveFile(MultipartFile file, String userId) throws RuntimeException {
        log.info("프로필 이미지 저장: " + file.getOriginalFilename());

        if (file == null || file.isEmpty()) {
            return null;
        }
        String savedName = userId + ".png";
        Path savePath = Paths.get(uploadPath, savedName).toAbsolutePath();
        try {
            Files.createDirectories(savePath.getParent());
            Files.copy(file.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패: " + e.getMessage());
        }
        return "images/" + savedName;
    }

    public void deleteFile(String filePath) {
        String absolutePath = "src/main/resources/static/" + filePath;
        log.info("파일 삭제 경로: " + absolutePath);
        File file = new File(absolutePath);
        if (file.exists()) {
            if (file.delete()) {
                log.info("파일 삭제 성공");
            } else {
                log.info("파일 삭제 실패");
            }
        } else {
            log.info("존재하지 않는 파일");
        }
    }
}
