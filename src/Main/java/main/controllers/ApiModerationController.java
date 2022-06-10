package main.controllers;

import main.api.request.ModerationRequest;
import main.api.response.ModerationResultResponse;
import main.service.ModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ApiModerationController {
    private final ModerationService moderationService;

    @Autowired
    public ApiModerationController(ModerationService moderationService) {
        this.moderationService = moderationService;
    }

    @PreAuthorize("hasAuthority('user:moderate')")
    @PostMapping("/moderation")
    public ResponseEntity<ModerationResultResponse> moderatePost(@RequestBody ModerationRequest moderationRequest, Principal principal){
        return moderationService.moderatePost(moderationRequest, principal);
    }
}
