package pl.edu.pb.wi.service;

import pl.edu.pb.wi.model.db.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> getRatingsByEconst(String econst);

    Rating createRating(Rating rating);

    Double getAvgRating(String eventId);

    Double getUserRating(String eventId, String userId);
}
