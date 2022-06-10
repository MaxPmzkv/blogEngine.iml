package main.service;

import main.api.request.PasswordRequest;
import main.api.request.ProfileRequest;
import main.api.response.*;
import main.model.User;
import main.model.repositories.CaptchaRepository;
import main.model.repositories.UserRepository;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private static final Map<String, LocalTime> hashStorageForRestorePassword = new ConcurrentHashMap<>();
    @Autowired
    UserRepository userRepository;

    @Autowired
    CaptchaRepository captchaRepository;

    public String uploadPhoto(MultipartFile newPhoto, Principal principal) throws Exception {
        String folder = "profile_photo";

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        String file = folder + "/" + user.getId();
        String resultPath = folder + "/" + user.getId();

        Path uploadDir = Paths.get(resultPath);
        if (!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path filePath = uploadDir.resolve(Objects.requireNonNull(newPhoto.getOriginalFilename()));
        File resizeFile = new File(String.valueOf(filePath));
        simpleResizeImage(newPhoto, resizeFile);


        return "/" + file + "/" + newPhoto.getOriginalFilename();
    }

    public void simpleResizeImage(MultipartFile newPhoto, File resizeFile) throws Exception {
        Thumbnails.of(newPhoto.getInputStream()).crop(Positions.CENTER_LEFT).size(36,36).keepAspectRatio(true).toFile(resizeFile);
    }

    public ResponseEntity editProfileReq(ProfileRequest profileRequest, Principal principal){
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setResult(true);
        String email = principal.getName();
        String name = profileRequest.getName();
        String newEmail = profileRequest.getEmail();
        String password = profileRequest.getPassword();
        boolean removePhoto = profileRequest.isRemovePhoto();


        if(!checkMyProfile(null, name, newEmail, password, principal, profileResponse)){
            return new ResponseEntity(profileResponse, HttpStatus.OK);
        }
        if(password == null && removePhoto == false){
            userRepository.editNameEmail(name, newEmail, principal.getName());
        }
        else if(!(password == null) && removePhoto == false){
            userRepository.editNameEmailPassword(name, email, BCrypt.hashpw(password, BCrypt.gensalt(12)), principal.getName());
        }

        return new ResponseEntity(profileResponse, HttpStatus.OK);
    }

    public ResponseEntity editProfile(MultipartFile file, String name, String email, String password, String removePhoto, Principal principal) throws Exception {

        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setResult(true);

        if (!checkMyProfile(file, name, email, password, principal, profileResponse)){
            return new ResponseEntity(profileResponse, HttpStatus.OK);
        }

        String filePath = uploadPhoto(file, principal);
        userRepository.editPasswordPhoto(name, email, BCrypt.hashpw(password, BCrypt.gensalt(12)), principal.getName(), filePath);

        return new ResponseEntity(profileResponse, HttpStatus.OK);
    }
    private Boolean checkMyProfile (MultipartFile file, String name, String email, String password, Principal principal, ProfileResponse profileResponse){
        ProfileEditingErrors errors = new ProfileEditingErrors();
        if(!name.matches("[\\w]+")){
           errors.setName("wrong name");
           profileResponse.setErrors(errors);
           profileResponse.setResult(false);
        }
        if(!(file == null) && file.getSize() > 5242880){
            errors.setPhoto("wrong picture size");
            profileResponse.setErrors(errors);
            profileResponse.setResult(false);
        }
        if((email.equals(principal.getName())) && userRepository.findUserByEmail(email).equals(email)){
            errors.setEmail("wrong email");
            profileResponse.setErrors(errors);
            profileResponse.setResult(false);
        }
        if(!(password == null) && password.length() < 6){
            errors.setPassword("wrong password");
            profileResponse.setErrors(errors);
            profileResponse.setResult(false);
        }
        return profileResponse.isResult();
    }
    public ResponseEntity checkRequest(PasswordRequest passwordRequest) {
        String captchaCode = captchaRepository.checkCaptcha(passwordRequest.getCaptchaSecret());
        PasswordResponseErrors resultResponseErrors = new PasswordResponseErrors();
        PasswordResponse passwordResponse = new PasswordResponse();
        passwordResponse.setResult(true);

        if((userRepository.findUserByCode(passwordRequest.getCode()) == null)){
            resultResponseErrors.setCode("Ссылка для восстановления пароля устарела.\n" +
                    " <a href=\n" +
                    " \\\"/auth/restore\\\">Запросить ссылку снова</a>");
            passwordResponse.setResult(false);
        }
        if(passwordRequest.getPassword().length() < 6){
            resultResponseErrors.setPassword("Пароль короче 6-ти символов");
            passwordResponse.setResult(false);
        }
        if(!captchaCode.equals(passwordRequest.getCaptcha())){
            resultResponseErrors.setCaptcha("Код с картинки введён неверно");
            passwordResponse.setResult(false);
        }

        return new ResponseEntity(passwordResponse, HttpStatus.OK);
    }
}
