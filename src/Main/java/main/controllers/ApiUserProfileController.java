package main.controllers;

import main.api.request.ProfileRequest;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ApiUserProfileController {
    private final UserService userService;

    @Autowired
    public ApiUserProfileController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/profile/my", consumes = {"multipart/form-data"})
    public ResponseEntity postProfile(
            @RequestPart(value = "photo", required=false) MultipartFile file,
            @RequestPart(value = "name", required=false) String name,
            @RequestPart(value = "email", required=false) String email,
            @RequestPart(value = "password", required=false)String password,
            @RequestPart(value = "removePhoto", required=false) String removePhoto,
            Principal principal) throws Exception {
        return userService.editProfile(file, name, email, password, removePhoto, principal);
    }


    @PostMapping(value = "/profile/my", consumes = {"application/json"})
    public ResponseEntity postProfileJSON(
            @RequestBody(required = false) ProfileRequest profileRequest,
            Principal principal) {

        return userService.editProfileReq(profileRequest, principal);

    }
}
