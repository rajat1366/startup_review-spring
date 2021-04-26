package com.StartupReview.controller;

import com.StartupReview.models.Rating;
import com.StartupReview.models.Startup;
import com.StartupReview.models.User;
import com.StartupReview.payload.request.RatingRequest;
import com.StartupReview.payload.response.MessageResponse;
import com.StartupReview.payload.response.StartupRatingResponse;
import com.StartupReview.security.services.UserDetailsImpl;

import com.StartupReview.service.RatingService;
import com.StartupReview.service.StartupService;
import com.StartupReview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/review")

public class RatingController {

    @Autowired
    StartupService startupService;

    @Autowired
    UserService userService;

    @Autowired
    RatingService ratingService;

    @GetMapping("/getReviewFromStartupAndUser")
    public ResponseEntity<?> getReviewFromStartupAndUser(@RequestParam long user_id,@RequestParam long startup_id){
        Optional <Rating> Rating = ratingService.getReviewByStartupIdAndUserId(user_id,startup_id);
        if(Rating.isPresent()){
            return ResponseEntity.ok(Rating.get());
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Review Not found!!!"));
        }
    }

    @GetMapping("/checkUserWrittenReview")
    public ResponseEntity<?> checkUserWrittenReview(@RequestParam long user_id,@RequestParam long startup_id){
        Boolean result = ratingService.existByUser_idAndStartup_id(user_id,startup_id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/startupInfo/{id}")
    public ResponseEntity<?> getStartupRating(@PathVariable("id") long id ){
        StartupRatingResponse startupRatingResponse = ratingService.getstartupsRating(id);
        if(startupRatingResponse != null ){
            return ResponseEntity.ok(startupRatingResponse);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Unable to find rating!!"));
        }
    }
    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateReview(@Valid @RequestBody RatingRequest review, @RequestParam("startupId") String startupId) {
        System.out.println(review.getId());
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findById(userDetails.getId()).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Startup startup = startupService.getstartupsById(Long.parseLong(startupId)).orElseThrow(() -> new RuntimeException("Error: Startup not found"));

        Rating rating = ratingService.getRatingById(Long.parseLong(review.getId())).orElseThrow(() -> new RuntimeException("Error: Rating not found"));

        rating.setTitle(review.getTitle());
        rating.setRating(Float.parseFloat(review.getRating()));
        rating.setDescription(review.getDescription());
        rating.setUser(user);
        rating.setStartup(startup);
        rating.setDateTime(LocalDateTime.now());

        Rating result = ratingService.saveRating(rating);
        if(result != null ){
            return ResponseEntity.ok(new MessageResponse("Review Updated sucessfully!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to update review!"));
        }

    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveReview(@Valid @RequestBody RatingRequest review, @RequestParam("startupId") String startupId) {
        try{
            UserDetailsImpl userDetails =
                    (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.findById(userDetails.getId()).orElseThrow(() -> new RuntimeException("Error: User is not found."));
            Startup startup = startupService.getstartupsById(Long.parseLong(startupId)).orElseThrow(() -> new RuntimeException("Error: Startup not found"));

            Rating rating = new Rating(review.getTitle(),
                                        Float.parseFloat(review.getRating()),
                                        review.getDescription(),
                                        LocalDateTime.now(),startup,user);

            Boolean reviewDone = ratingService.existByUser_idAndStartup_id(user.getId(),startup.getId());
            if(reviewDone){
                return ResponseEntity.badRequest().body(new MessageResponse("Error: You have already written the review for this startup!! "));
            } else {
                Rating result = ratingService.saveRating(rating);

                if(result!= null)
                    return ResponseEntity.ok(new MessageResponse("Review added successfully!"));
                else
                    return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to add review!"));
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to add new review!"));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRatingsbyId(@PathVariable("id") long id ){
        Optional<Rating> rating = ratingService.getratingsById(id);
        if(rating.isPresent()){
            return ResponseEntity.ok(rating.get());
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Reviews Not found!!!"));
        }
    }

//    @PostMapping("/add")
//    public ResponseEntity<?> saveRating(@RequestBody RatingRequest ratingRequest) {
//
//        return ResponseEntity.ok(new MessageResponse("Review added"));
//    }

}
