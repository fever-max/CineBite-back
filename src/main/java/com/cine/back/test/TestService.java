package com.cine.back.test;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cine.back.config.file.S3Uploader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final TestRepository testRepository;
    private final TestFileRepository testFileRepository;
    private final S3Uploader s3Uploader;

    public void save(TestEntity userInfo) {
        if (userInfo == null) {
            log.error("유저 정보 null");
            return;
        }
        testRepository.save(userInfo);
    }

    public List<TestEntity> findAll() {
        List<TestEntity> testEntity = testRepository.findAll();
        return testEntity;
    }

    public void saveFile(MultipartFile imgFile, String imgText) throws IOException {
        if (!imgFile.isEmpty()) {
            String storedFileName = s3Uploader.upload(imgFile, "images"); // s3 버킷에 images 디렉토리에 업로드
            TestFileEntity testFileEntity = new TestFileEntity();
            testFileEntity.setImgText(imgText);
            testFileEntity.setImgUrl(storedFileName);
            testFileRepository.save(testFileEntity);
        }
    }

    public List<TestFileEntity> findImg() {
        List<TestFileEntity> testFileEntity = testFileRepository.findAll();
        return testFileEntity;
    }

}
