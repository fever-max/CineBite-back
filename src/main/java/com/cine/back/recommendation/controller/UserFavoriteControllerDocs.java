package com.cine.back.recommendation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cine.back.recommendation.entity.UserFavorite;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;
import java.util.List;

@Tag(name = "Favorite", description = "a user's favorite list")
public interface UserFavoriteControllerDocs {
    
    // 찜하기
    @Operation(summary = "영화 찜하기", description = "해당 영화를 찜하기 목록에 추가합니다.")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "찜목록에 추가되었습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인해 추가되지않았습니다."),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 찜 목록 추가 실패"),
    })
    ResponseEntity<?> addToFavorite(
        @RequestParam(value = "userId") String userId,
        @RequestParam(value = "movieId") int movieId);
    
    // 찜하기 취소 
    @Operation(summary = "영화 찜하기 취소", description = "해당 영화를 찜하기 목록에서 삭제합니다.")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "찜목록에서 삭제되었습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인해 삭제되지않았습니다."),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 찜 목록 삭제 실패"),
    })
    ResponseEntity<?> deleteToFavorite (
        @RequestParam(value = "userId") String userId,
        @RequestParam(value = "movieId") int movieId);
    
    
    // 사용자의 찜목록 조회
    @Operation(summary = "사용자의 영화 찜 목록 조회", description = "해당 영화를 찜하기 목록에 추가합니다.")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "찜목록이 조회되었습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인해 조회되지않았습니다."),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 찜 목록 조회 실패"),
    })
    ResponseEntity<Optional<List<UserFavorite>>>getToFavorite(
                    @PathVariable(value = "userId") String userId);
}
