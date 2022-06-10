package main.controllers;

import main.api.response.InitResponse;
import main.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class ApiGeneralController {
    private final InitResponse initResponse;
    private final StorageService storageService;

    @Autowired
    public ApiGeneralController(InitResponse initResponse, StorageService storageService) {
        this.initResponse = initResponse;
        this.storageService = storageService;

    }

    @GetMapping("/init")
    public InitResponse init(){return initResponse; }

    @PostMapping("/image")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile file) throws Exception {
        return storageService.upload(file);
    }
}
