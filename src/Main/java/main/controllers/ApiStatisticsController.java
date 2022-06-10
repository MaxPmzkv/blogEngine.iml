package main.controllers;

import main.api.response.StatisticsResponse;
import main.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ApiStatisticsController {
    private final StatisticsService statisticsService;

    @Autowired
    public ApiStatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PreAuthorize("hasAuthority('user:write')")
    @GetMapping("/statistics/my")
    public StatisticsResponse getMyStats(Principal principal){
        return statisticsService.getMyStats(principal);
    }

    @GetMapping("statistics/all")
    public ResponseEntity getAllStats(Principal principal){
        return statisticsService.getAllStats(principal);
    }
}
