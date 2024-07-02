package com.cine.back.board.comment.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.cine.back.board.comment.dto.ReplyResponseDto;
import com.cine.back.board.comment.dto.CommentRequestDto;
import com.cine.back.board.comment.entity.CommentEntity;
import com.cine.back.board.comment.entity.ReplyEntity;
import com.cine.back.board.comment.repository.ReplyRepository;
import com.cine.back.board.util.EntityUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentMapper commentMapper;
    private final EntityUtil entityUtil;

    @Transactional
    public ReplyResponseDto writeReply(Long commentNo, CommentRequestDto requestDto) {
        try {
            CommentEntity commentEntity = entityUtil.findCommentById(commentNo);
            entityUtil.updateCommentCount(commentEntity.getPost());
            ReplyEntity replyEntity = commentMapper.toReplyEntity(commentEntity, requestDto);
            replyRepository.save(replyEntity);
            log.info("대댓글 저장 성공 /comment No: {}, Reply No", commentNo, replyEntity.getReplyNo());
            ReplyResponseDto replyResponseDto = commentMapper.toReplyResponseDto(replyEntity);
            return replyResponseDto;
        } catch (NoSuchElementException e) {
            log.error("조회할 부모 댓글이 없음: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public List<ReplyResponseDto> getAllReplies(Long commentNo) {
        try {
            CommentEntity comment = entityUtil.findCommentById(commentNo);
            List<ReplyEntity> replyEntities = replyRepository.findByComment_CommentNo(comment.getCommentNo());
            List<ReplyResponseDto> replyResponseDtos = commentMapper.toReplyResponseDtos(replyEntities);
            log.info("대댓글 조회 성공 / comment No: {}, 총 댓글 수 {}개", commentNo, replyResponseDtos.size());
            return replyResponseDtos;
        } catch (NoSuchElementException e) {
            log.error("조회할 부모 댓글이 없음: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void deleteReply(Long commentNo, Long replyNo) {
        try {
            CommentEntity comment = entityUtil.findCommentById(commentNo);
            entityUtil.deleteCommentCount(comment.getPost());
            ReplyEntity replyEntity = entityUtil.findReplyById(replyNo);
            replyRepository.delete(replyEntity);
            log.info("댓글 삭제 성공 / reply No: {}", replyNo);
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public ReplyResponseDto modifyReply(Long replyNo, CommentRequestDto commentRequestDto) {
        try {
            ReplyEntity reply = entityUtil.findReplyById(replyNo);
            ReplyEntity updateReply = commentMapper.updateReplyEntity(reply, commentRequestDto);
            ReplyResponseDto responseDto = commentMapper.toReplyResponseDto(updateReply);
            log.info("대댓글 수정 성공 / reply No: {}", replyNo);
            return responseDto;
        } catch (NoSuchElementException e) {
            log.error("수정할 대댓글이 없음: {}", e.getMessage());
            throw e;
        }
    }

}
