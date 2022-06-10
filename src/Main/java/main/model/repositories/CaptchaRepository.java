package main.model.repositories;

import main.model.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaCode, Integer> {

    @Modifying
    @Query(value = "INSERT INTO captcha_codes (code, secret_code, time) VALUES (:code, :secret_code, :time)", nativeQuery = true)
    void insertCaptcha(@Param("code") String code,
                       @Param("secret_code") String secretCode,
                       @Param("time")Date date);

    @Query(value = "SELECT c.code FROM captcha_codes c WHERE c.secret_code = :captchaCode", nativeQuery = true)
    String checkCaptcha(@Param("captchaCode") String code);
}
