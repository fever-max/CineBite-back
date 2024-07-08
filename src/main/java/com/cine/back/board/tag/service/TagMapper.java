package com.cine.back.board.tag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.cine.back.board.post.entity.PostTagEntity;
import com.cine.back.board.tag.dto.TagResponseDto;

@Component
public class TagMapper {

    public List<TagResponseDto> toResponseDtos(List<PostTagEntity> postEntities) {
        return postEntities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public TagResponseDto toResponseDto(PostTagEntity postEntity) {
        return new TagResponseDto(postEntity.getTagNo(), postEntity.getTagName());
    }

}
