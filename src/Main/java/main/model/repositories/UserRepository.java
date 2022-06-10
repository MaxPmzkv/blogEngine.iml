package main.model.repositories;

import main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT email FROM users u WHERE u.email = :email", nativeQuery = true)
    String findUserByEmail(@Param("email") String email);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (email, name, is_moderator, password, reg_time) VALUES (:email, :name, :moderation, :password, :time)", nativeQuery = true)
    void insertUser(@Param("email") String email,@Param("moderation") int mod, @Param("name") String name, @Param("time") Date time, @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET name = :newName, email = :newEmail WHERE email = :email", nativeQuery = true)
    void editNameEmail(@Param("newName") String newName, @Param("newEmail") String newEmail, @Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET name = :newName, email = :newEmail, password = :password WHERE email = :email", nativeQuery = true)
    void editNameEmailPassword(@Param("newName") String newName, @Param("newEmail") String newEmail, @Param("password") String password, @Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET name = :newName, email = :newEmail, password = :password, photo = :photo WHERE email = :email", nativeQuery = true)
    void editPasswordPhoto(@Param("newName") String newName, @Param("newEmail") String newEmail, @Param("password") String password, @Param("email") String email, @Param("photo") String photo);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET code = :code WHERE email = :email", nativeQuery = true)
    void saveCode(@Param("email") String email, @Param("code") String code);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE users SET password = :password WHERE code = :code",
            nativeQuery = true)
    void changePassword(@Param("password") String password, @Param("code") String code);

    @Query(value = "SELECT * FROM users u where code =:code", nativeQuery = true)
    User findUserByCode(@Param("code") String code);
}
