package com.StartupReview.service;

import com.StartupReview.models.Rating;
import com.StartupReview.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating saveRating(Rating rating) { return ratingRepository.save(rating); }
    public Page<Rating> getRatings(Pageable pageable){
        return ratingRepository.findAllByOrderByDateTimeDesc(pageable);
    }

    public Optional<Rating> getratingsById(long id) {
        return ratingRepository.findById(id);
    }
}
