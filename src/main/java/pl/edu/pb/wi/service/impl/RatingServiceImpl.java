package pl.edu.pb.wi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.model.db.Rating;
import pl.edu.pb.wi.repository.RatingRepository;
import pl.edu.pb.wi.service.RatingService;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public List<Rating> getRatingsByEconst(String econst) {
        return ratingRepository.findByEventId(econst);
    }

    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }
}
