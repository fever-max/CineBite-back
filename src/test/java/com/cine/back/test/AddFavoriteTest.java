// package com.cine.back.test;

// import static org.mockito.ArgumentMatchers.anyInt;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.context.event.ApplicationReadyEvent;
// import org.springframework.context.event.EventListener;

// import com.cine.back.favorite.entity.UserFavorite;
// import com.cine.back.favorite.repository.UserFavoriteRepository;

// import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
// public class AddFavoriteTest {

//     @Autowired
//     private final UserFavoriteRepository userFavoriteRepository;

//     @EventListener(ApplicationReadyEvent.class)
//     public void initData() {
//         userFavoriteRepository.save(UserFavorite.builder()
//                 .userId("유저1")
//                 .movieId(11)
//                 .posterPath("포스터1")
//                 .title("제목1")
//                 .tomatoScore(10)
//                 .build());
//         userFavoriteRepository.save(UserFavorite.builder()
//                 .userId("유저2")
//                 .movieId(11)
//                 .posterPath("포스터2")
//                 .title("제목2")
//                 .tomatoScore(10)
//                 .build());
//         userFavoriteRepository.save(UserFavorite.builder()
//                 .userId("유저3")
//                 .movieId(11)
//                 .posterPath("포스터3")
//                 .title("제목3")
//                 .tomatoScore(10)
//                 .build());
//         userFavoriteRepository.save(UserFavorite.builder()
//                 .userId("유저4")
//                 .movieId(11)
//                 .posterPath("포스터4")
//                 .title("제목4")
//                 .tomatoScore(10)
//                 .build());
//         userFavoriteRepository.save(UserFavorite.builder()
//                 .userId("유저5")
//                 .movieId(11)
//                 .posterPath("포스터5")
//                 .title("제목5")
//                 .tomatoScore(10)
//                 .build());
 
//     }

// }
