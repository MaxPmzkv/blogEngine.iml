package main.controllers;

import main.api.request.LoginRequest;
import main.api.request.RegistrationRequest;
import main.api.response.*;
import main.model.repositories.UserRepository;
import main.service.CaptchaService;
import main.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CaptchaService captchaService;
    private final RegistrationService registrationService;

    @Autowired
    public ApiAuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                             CaptchaService captchaService, RegistrationService registrationService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.captchaService = captchaService;
        this.registrationService = registrationService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        Authentication auth = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();


        return ResponseEntity.ok(getLoginResponse(user.getUsername()));

    }

    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<LogoutResponse> logout(Principal principal){
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(new LogoutResponse(true), HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<LoginResponse> check(Principal principal){
        if(principal == null){
            return ResponseEntity.ok(new LoginResponse());
        }
        return ResponseEntity.ok(getLoginResponse(principal.getName()));
    }

    private LoginResponse getLoginResponse(String email){
        main.model.User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(email));
        UserLoginResponse userResponse = new UserLoginResponse();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setName(currentUser.getName());
        userResponse.setModeration(currentUser.getIsModerator() == 1);
        userResponse.setId(currentUser.getId());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userResponse);

        return loginResponse;
    }

    @GetMapping("/captcha")
    public AuthCaptchaResponse getCaptcha() throws IOException {

        return captchaService.getCaptcha();
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(
            @RequestBody RegistrationRequest registrationRequest){
        AuthRegUser regUser = registrationService.checkReg(
                registrationRequest.getName(),
                registrationRequest.getEmail(),
                registrationRequest.getPassword(),
                registrationRequest.getCaptcha());
        if(regUser.checkingForErrors()){
            userRepository.insertUser(registrationRequest.getEmail(), 0,
                    registrationRequest.getName(),
                    new Date(),
                    BCrypt.hashpw(registrationRequest.getPassword(), BCrypt.gensalt(12)));
        }
        return ResponseEntity.ok(new RegistrationResponse(regUser));
    }
    }

