package main.controllers;

import main.api.response.TagsResponse;
import main.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiTagsController {
    private final TagService tagService;

    @Autowired
    public ApiTagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tag")
    private TagsResponse tagResponse(@RequestParam(required = false, defaultValue = "") String query) {
        return tagService.getTags(query);
    }
}
