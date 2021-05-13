package com.StartupReview.service;

import com.StartupReview.models.Rating;
import com.StartupReview.models.Startup;
import com.StartupReview.models.User;
import com.StartupReview.repository.RatingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    RatingRepository ratingRepository;

    @InjectMocks
    RatingService ratingService;

    @Test
    void saveRating() {
        Rating rating = new Rating();
        when(ratingRepository.save(rating)).thenReturn(rating);
        assertEquals(rating, ratingService.saveRating(rating));
    }

    @Test
    void existByUser_idAndStartup_id() {
        Rating rating= new Rating();
        User user = new User();
        Startup startup = new Startup();
        user.setId(1L);
        startup.setId(1L);
        rating.setUser(user);
        rating.setStartup(startup);

        when(ratingRepository.existsByUser_idAndStartup_id(user.getId(), startup.getId())).thenReturn(Boolean.TRUE);
        Boolean result = ratingService.existByUser_idAndStartup_id(user.getId(), startup.getId());
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void getstartupsRating() {
    }

    @Test
    void getReviewByStartupIdAndUserId() {
        Rating rating= new Rating();
        User user = new User();
        Startup startup = new Startup();
        user.setId(1L);
        startup.setId(1L);
        rating.setUser(user);
        rating.setStartup(startup);

        when(ratingRepository.FindByUser_idAndStartup_id(user.getId(), startup.getId())).thenReturn(Optional.of(rating));
        Optional<Rating> result = ratingService.getReviewByStartupIdAndUserId(user.getId(), startup.getId());
        assertNotNull(result);

    }

    @Test
    void getRatingById() {
        Rating rating = new Rating();
        rating.setId(1L);
        when(ratingRepository.findById(rating.getId())).thenReturn(Optional.of(rating));
        Optional<Rating> result = ratingService.getRatingById(rating.getId());
        assertNotNull(result);
    }

    @Test
    void getRatings() {
        Pageable pageable = PageRequest.of(0,3);
        Startup startup = new Startup();
        startup.setId(1L);
//        Rating rating = new Rating();
//        rating.setStartup(startup);
        List<Rating> list = new ArrayList<>();
        Page<Rating> ratingPage = new PageImpl(list);

        when(ratingRepository.FindByStartupOrderByDateTimeDesc(startup.getId(),pageable)).thenReturn(ratingPage);
        Page<Rating> result = ratingService.getRatings(startup.getId(),pageable);
        Assertions.assertThat(result).isEmpty();
    }
}