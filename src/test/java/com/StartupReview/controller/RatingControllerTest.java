package com.StartupReview.controller;

import com.StartupReview.models.Rating;
import com.StartupReview.models.Startup;
import com.StartupReview.models.User;
import com.StartupReview.security.jwt.AuthEntryPointJwt;
import com.StartupReview.security.jwt.JwtUtils;
import com.StartupReview.security.services.UserDetailsServiceImpl;
import com.StartupReview.service.RatingService;
import com.StartupReview.service.StartupService;
import com.StartupReview.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = RatingController.class)
class RatingControllerTest {

    @MockBean
    private RatingService ratingService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private UserService userService;
    @MockBean
    private StartupService startupService;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;



    @Autowired
    private MockMvc mockMvc;
    @Test
    void shouldReturnRatingsbyId() throws Exception {
        Date dt = new Date();
        LocalDateTime now = LocalDateTime.now();

        User user = new User(1L,"user1","user1@gmail.com","name","password");
        Startup startup = new Startup(1L,"zoom","video conferencing app",user,dt,now,"video","testLink");
        Rating rating = new Rating(123L,"Test Rating",5.5F,"Test Description",now,startup,user);

        Mockito.when(ratingService.getRatingById(123L)).thenReturn(Optional.of(rating));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/review/?id=123"))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",Matchers.is("Test Rating")));



    }
}