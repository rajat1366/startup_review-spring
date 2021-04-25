package com.StartupReview.controller;

import com.StartupReview.models.Rating;
import com.StartupReview.models.Startup;
import com.StartupReview.payload.request.RatingRequest;
import com.StartupReview.payload.response.MessageResponse;
import com.StartupReview.service.RatingService;
import com.StartupReview.service.StartupService;
import com.StartupReview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rating")
public class RatingController {

    @Autowired
    StartupService startupService;

    @Autowired
    UserService userService;

    @Autowired
    RatingService ratingService;

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

    @PostMapping("/add")
    public ResponseEntity<?> saveRating(@RequestBody RatingRequest ratingRequest) {

        return ResponseEntity.ok(new MessageResponse("Review added"));
    }


}
