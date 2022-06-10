package main.service;

import lombok.Data;
import main.api.request.RestoreResultResponse;
import main.model.User;
import main.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Data
@Service
public class RestorationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailService mailService;

    public RestoreResultResponse checkEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        String codeHash = UUID.randomUUID().toString();

        userRepository.saveCode(email, codeHash);
        String message = String.format(
                "Hello!, %s! \n" +
                        "Here is your restoration link: http://localhost:8080/login/change-password/%s\n",
                user.getName(),
                codeHash
        );

        mailService.sendMail(email, "Код активации: ", message);

        return new RestoreResultResponse(true);
    }


}
