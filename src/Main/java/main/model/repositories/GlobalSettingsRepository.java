package main.model.repositories;

import main.model.GlobalSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GlobalSettingsRepository extends JpaRepository<GlobalSetting, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM global_settings gs WHERE gs.code = :code")
    GlobalSetting findGlobalSettings(@Param("code") String code);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE global_settings gs SET gs.value = :value WHERE gs.code = :code")
    void insertSettings(@Param("code") String code, @Param("value") String value);
}
