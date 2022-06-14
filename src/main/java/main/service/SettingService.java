package main.service;

import main.api.request.SettingsRequest;
import main.api.response.SettingsResponse;
import main.model.repositories.GlobalSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    @Autowired
    GlobalSettingsRepository globalSettingsRepository;

    public SettingsResponse getGlobalSettings() {
        SettingsResponse settingsResponse = new SettingsResponse();
        settingsResponse.setMultiuserMode(true);
        settingsResponse.setStatisticIsPublic(true);
        return settingsResponse;
    }

    public ResponseEntity<Boolean> putGlobalSettings(SettingsRequest settingsRequest){
        String multiuserMode = settingsRequest.isMultiUserMode() ? "YES" : "NO";
        String postPreModeration = settingsRequest.isPostPreModeration() ? "YES" : "NO";
        String statisticsIsPublic = settingsRequest.isStatisticsIsPublic() ? "YES" : "NO";

        globalSettingsRepository.insertSettings("MULTIUSER_MODE", multiuserMode);
        globalSettingsRepository.insertSettings("POST_PREMODERATION",postPreModeration);
        globalSettingsRepository.insertSettings("STATISTICS_IS_PUBLIC", statisticsIsPublic);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
