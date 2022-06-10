package main.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class StorageService {

    public ResponseEntity<Object> upload(MultipartFile file) throws Exception {
        String folder = "upload";
        String[] uuidPath = UUID.randomUUID().toString().split("-", 3);
        String resultPath = folder + "/" + uuidPath[0] + "/" + uuidPath[1] + "/" + uuidPath[2];
        Path uploadDir = Paths.get(resultPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path filePath = uploadDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


        return new ResponseEntity<>("/" + resultPath + "/" + file.getOriginalFilename(), HttpStatus.OK);
    }
}
