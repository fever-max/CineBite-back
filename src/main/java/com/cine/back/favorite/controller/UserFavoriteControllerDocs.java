package com.cine.back.favorite.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cine.back.favorite.dto.FavoriteRequestDto;
import com.cine.back.favorite.dto.FavoriteResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;
import java.io.IOException;
import java.util.List;

@Tag(name = "Favorite", description = "유저의 Favorite List")
public interface UserFavoriteControllerDocs {
    
    // @@ 찜하기
    @Operation(summary = "영화 찜하기", description = "해당 영화를 찜하기 목록에 추가합니다.")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "찜목록에 추가되었습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인해 추가되지 않았습니다."),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 찜 목록 추가 실패"),
    })
    public ResponseEntity<Optional<FavoriteResponseDto>> saveToFavorite(
        @RequestBody FavoriteRequestDto favoriteDto) throws IOException;

    // @@ 찜 삭제
    @Operation(summary = "영화 찜하기 삭제", description = "해당 영화를 찜하기 목록에서 삭제합니다.")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "찜목록에서 삭제되었습니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인해 삭제되지 않았습니다."),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 찜 목록 삭제 실패"),
    })
        ResponseEntity<String> deleteToFavorite (
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "movieId") int movieId) throws IOException;


    @Operation(summary = "사용자의 영화 찜 목록 조회", description = "해당 영화를 찜하기 목록에 추가합니다.")
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "찜목록이 조회되었습니다."),
                @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인해 조회되지않았습니다."),
                @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 찜 목록 조회 실패"),
    })
        ResponseEntity<List<FavoriteResponseDto>> getToFavoriteList(
            @RequestParam(value = "userId") String userId);

    @Operation(summary = "사용자의 찜 목록 페이징", description = "해당 영화를 페이징합니다.")
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "찜목록에 페이징되었습니다."),
                @ApiResponse(responseCode = "400", description = "잘못된 요청으로 인해 페이징되지않았습니다."),
                @ApiResponse(responseCode = "500", description = "서버 내부 오류로 인한 찜 목록 페이징 실패"),
    })
        ResponseEntity<Page<FavoriteResponseDto>> getToFavoriteListPaged(
            // @RequestParam(value = "userId") String userId, 
            @RequestParam(value ="userId")String userId, Pageable pageable);
}
