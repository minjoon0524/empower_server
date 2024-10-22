package com.inhatc.empower.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {
    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    // 폴더를 만들어주는 용도
    @PostConstruct
    public void init() {
        File tempFolder = new File(uploadPath);
        if(!tempFolder.exists()) {
            tempFolder.mkdir();
        }
        uploadPath = tempFolder.getAbsolutePath();
        log.info("-------------------------------------");
        log.info(uploadPath);
    }

    // 프로필 사진을 저장하는 메서드
    public String saveProfilePicture(MultipartFile file) throws RuntimeException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String savedName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path savePath = Paths.get(uploadPath, savedName);
        try {
            Files.copy(file.getInputStream(), savePath); // 원본 파일 업로드

            String contentType = file.getContentType();
            if (contentType != null && contentType.startsWith("image")) { // 이미지 여부 확인
                Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName);
                Thumbnails.of(savePath.toFile())
                        .size(200, 200)
                        .toFile(thumbnailPath.toFile());
            }

            return savedName; // 저장된 파일 이름 반환
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<Resource> getFile(String fileName) {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        if (!resource.exists()) {
            resource = new FileSystemResource(uploadPath + File.separator + "default.jpeg");
        }
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    // 서버 내부에서 파일의 삭제
    public void deleteFiles(List<String> fileNames) {
        if (fileNames == null || fileNames.size() == 0) {
            return;
        }
        fileNames.forEach(fileName -> {
            // 썸네일이 있는지 확인하고 삭제
            String thumbnailFileName = "s_" + fileName;
            Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName);
            Path filePath = Paths.get(uploadPath, fileName);
            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }
}
