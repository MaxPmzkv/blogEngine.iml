package main.controllers;

import main.api.request.PasswordRequest;
import main.api.request.RestoreRequest;
import main.api.request.RestoreResultResponse;
import main.service.RestorationService;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ApiPassRestoreController {

    private final RestorationService restorationService;
    private final UserService userService;

    @Autowired
    public ApiPassRestoreController(RestorationService restorationService, UserService userService) {
        this.restorationService = restorationService;
        this.userService = userService;
    }

    @PostMapping("/restore")
    public ResponseEntity<RestoreResultResponse> postRestore(
            @RequestBody RestoreRequest restoreRequest) {

        return ResponseEntity.ok(restorationService.checkEmail(restoreRequest.getEmail()));
    }
    @PostMapping("/password")
    public ResponseEntity postPassword(
            @RequestBody PasswordRequest passwordRequest) {

        return userService.checkRequest(passwordRequest);
    }
}
