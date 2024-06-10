package com.cine.back.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cine.back.board.entity.BoardEntity;
import com.cine.back.board.entity.BoardTagEntity;
import com.cine.back.board.entity.BoardTagMapEntity;
import com.cine.back.board.repository.BoardTagMapRepository;
import com.cine.back.board.repository.BoardTagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardTagService {

    private final BoardTagMapRepository boardTagMapRepository;
    private final BoardTagRepository boardTagRepository;

    public void saveTags(BoardEntity savedBoard, List<String> tagNames) {
        if (tagNames != null && !tagNames.isEmpty()) {
            for (String tagName : tagNames) {
                BoardTagEntity tag = new BoardTagEntity();
                tag.setName(tagName);
                boardTagRepository.save(tag);
                BoardTagMapEntity mapEntity = new BoardTagMapEntity();
                mapEntity.setBoard(savedBoard);
                mapEntity.setTag(tag);
                boardTagMapRepository.save(mapEntity);
            }
        }
    }

    public List<String> updateTags(BoardEntity boardEntity, List<String> newTagNames) {
        List<BoardTagMapEntity> tagMappings = boardEntity.getBoardTagMappings();
        boardTagMapRepository.deleteAll(tagMappings);
        saveTags(boardEntity, newTagNames);

        return newTagNames;
    }

    public List<String> getTagNamesForBoard(BoardEntity board) {
        List<String> tagNames = new ArrayList<>();
        List<BoardTagMapEntity> tagMappings = board.getBoardTagMappings();
        for (BoardTagMapEntity mapping : tagMappings) {
            BoardTagEntity tag = mapping.getTag();
            tagNames.add(tag.getName());
        }
        return tagNames;
    }
}
