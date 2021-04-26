package com.StartupReview.payload.response;

public class StartupRatingResponse {
    private float avgRating;

    private long totalRatings;

    public StartupRatingResponse(float avgRating, long totalRatings) {
        this.avgRating = avgRating;
        this.totalRatings = totalRatings;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }
}
