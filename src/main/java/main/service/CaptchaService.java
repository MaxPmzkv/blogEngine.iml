package main.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import main.api.response.AuthCaptchaResponse;
import main.model.repositories.CaptchaRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

@Service
@Transactional
public class CaptchaService {
    @Autowired
    CaptchaRepository captchaRepository;

    public AuthCaptchaResponse getCaptcha() throws IOException{
        Cage cage = new GCage();
        String token = cage.getTokenGenerator().next();
        String secretCode = secretKeyGenerator();
        String imageConvert = imageToBase64Converter(token, cage);

        AuthCaptchaResponse authCaptchaResponse = new AuthCaptchaResponse();
        authCaptchaResponse.setSecret(secretCode);
        authCaptchaResponse.setImage(imageConvert);

        captchaRepository.insertCaptcha(token, secretCode, new Date());

        return authCaptchaResponse;
 }

    private String imageToBase64Converter(String token, Cage cage) throws IOException{

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(ImageResizer(cage.drawImage(token)), "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
    }

    private String secretKeyGenerator(){
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private BufferedImage ImageResizer (BufferedImage image){

        int newWidth = 100;
        int newHeight = 35;

        BufferedImage newImage = new BufferedImage(
                newWidth, newHeight, BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = newImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.drawImage(image, 0, 0, newWidth, newHeight, null);

        g.dispose();

        return newImage;
    }

}
