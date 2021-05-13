package com.StartupReview.service;

import com.StartupReview.models.Rating;
import com.StartupReview.models.Startup;
import com.StartupReview.models.User;
import com.StartupReview.repository.RatingRepository;
import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository ;

    @Captor
    private ArgumentCaptor<Rating> savedRatingCaptor;

    RatingService ratingService;

    @BeforeEach
    public void setup(){
         ratingService = new RatingService(ratingRepository);
    }
    @Test
    @DisplayName("Should Find Rating By Id")
    void shouldFindRatingById(){


            Date dt = new Date();
            LocalDateTime now = LocalDateTime.now();

            User user = new User(1L,"user1","user1@gmail.com","name","password");
            Startup startup = new Startup(1L,"zoom","video conferencing app",user,dt,now,"video","testLink");
            Rating rating = new Rating(123L,"Test Rating",5.5F,"Test Description",now,startup,user);
            Mockito.when(ratingRepository.findById(123L)).thenReturn(Optional.of(rating));

            Optional<Rating> actualRating = ratingService.getRatingById(123L);
            assertThat(actualRating.get().getId()).isEqualTo(rating.getId());

    }
    @Test
    @DisplayName("Should Save Rating")
    void shouldSaveRating(){

        Date dt = new Date();
        LocalDateTime now = LocalDateTime.now();

        User user = new User(1L,"user1","user1@gmail.com","name","password");
        Startup startup = new Startup(1L,"zoom","video conferencing app",user,dt,now,"video","testLink");
        Rating rating = new Rating(123L,"Test Rating",5.5F,"Test Description",now,startup,user);

        ratingService.saveRating(rating);
        Mockito.verify(ratingRepository,Mockito.times(1)).save(savedRatingCaptor.capture());

        assertThat(savedRatingCaptor.getValue().getId()).isEqualTo(123L);
        assertThat(savedRatingCaptor.getValue().getTitle()).isEqualTo("Test Rating");

    }
}