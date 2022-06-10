package main.controllers;

import main.api.request.SettingsRequest;
import main.api.response.SettingsResponse;
import main.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class SettingsController {
    private final SettingService settingService;

    @Autowired
    public SettingsController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("/settings")
    public SettingsResponse settingsResponse(){
        return settingService.getGlobalSettings();
    }

    @PutMapping("/settings")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity putSettings(@RequestBody(required = false) SettingsRequest settingsRequest,
                                      Principal principal){
        return settingService.putGlobalSettings(settingsRequest);
    }
}
