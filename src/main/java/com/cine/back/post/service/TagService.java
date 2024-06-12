package com.cine.back.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cine.back.post.entity.PostEntity;
import com.cine.back.post.entity.PostTagEntity;
import com.cine.back.post.entity.PostTagMapEntity;
import com.cine.back.post.repository.TagMapRepository;
import com.cine.back.post.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagMapRepository tagMapRepository;
    private final TagRepository tagRepository;

    public void saveTags(PostEntity savedBoard, List<String> tagNames) {
        if (tagNames != null && !tagNames.isEmpty()) {
            for (String tagName : tagNames) {
                PostTagEntity tag = new PostTagEntity();
                tag.setTagName(tagName);
                tagRepository.save(tag);
                PostTagMapEntity mapEntity = new PostTagMapEntity();
                mapEntity.setPost(savedBoard);
                mapEntity.setTag(tag);
                tagMapRepository.save(mapEntity);
            }
        }
    }

    public List<String> updateTags(PostEntity postEntity, List<String> newTagNames) {
        List<PostTagMapEntity> tagMappings = postEntity.getPostTagMappings();
        tagMapRepository.deleteAll(tagMappings);
        saveTags(postEntity, newTagNames);
        return newTagNames;
    }

    public List<String> getTagNamesForBoard(PostEntity postEntity) {
        List<String> tagNames = new ArrayList<>();
        List<PostTagMapEntity> tagMappings = postEntity.getPostTagMappings();
        for (PostTagMapEntity mapping : tagMappings) {
            PostTagEntity tag = mapping.getTag();
            tagNames.add(tag.getTagName());
        }
        return tagNames;
    }
}
