package main.service;

import main.api.response.GeneralPostsAmountByTag;
import main.api.response.TagResponse;
import main.api.response.TagsResponse;
import main.model.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public TagsResponse getTags(String query) {

        List<GeneralPostsAmountByTag> tagList = tagRepository.getTags();

        List<TagResponse> tagResponseList = new ArrayList<>();
        double normalWeight = (double) tagList.get(0).getCount() / tagList.size();
        for (GeneralPostsAmountByTag g : tagList) {
            TagResponse tagResponse = new TagResponse(g.getName(), ((double) g.getCount()
                    / tagList.size() / normalWeight));

            tagResponseList.add(tagResponse);
        }
        TagsResponse tagsResponse = new TagsResponse();
        tagsResponse.setTags(tagResponseList);

        return tagsResponse;

    }
  }



