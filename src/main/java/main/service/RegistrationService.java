package main.service;

import main.api.response.AuthRegUser;
import main.model.repositories.CaptchaRepository;
import main.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class RegistrationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CaptchaRepository captchaRepository;

    public AuthRegUser checkReg(String name,
                                String email,
                                String password,
                                String captcha){

        AuthRegUser userError = new AuthRegUser();
        userError.setEmail(checkEmail(email));
        userError.setName(checkName(name));
        userError.setPassword(checkPassword(password));
        userError.setCaptcha(checkCaptcha(captcha));

        return userError;
    }

    private String checkName(String name){
        if (!name.matches("[\\w]+")){
            return "Имя указано неверно";
        }
        else {return "";}
    }

    private String checkPassword(String password){
        if(password.length() < 6){
            return "Пароль короче 6-ти символов";
        }
        else {return "";}
    }

    private String checkEmail(String email){
        if(userRepository.findByEmail(email).isPresent()){
            return "Этот e-mail уже зарегистрирован";
        }
        else {return "";}
    }

    private String checkCaptcha(String secretCaptcha){
        if(!secretCaptcha.equals(captchaRepository.checkCaptcha(secretCaptcha))){
            return "Код с картинки введён неверно";
        }
        else {return "";}
    }



}
