package pl.edu.pb.wi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.model.db.Rating;
import pl.edu.pb.wi.model.db.response.AvgRatingResult;
import pl.edu.pb.wi.repository.RatingRepository;
import pl.edu.pb.wi.service.RatingService;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Rating> getRatingsByEconst(String econst) {
        return ratingRepository.findByEventId(econst);
    }

    @Override
    public Rating createRating(Rating rating) {
        Optional<Rating> optionalRating = ratingRepository.findByUserIdAndEventId(rating.getUserId(), rating.getEventId());
        if (optionalRating.isPresent()) {
            Rating found = optionalRating.get();
            found.setRating(rating.getRating());
            return ratingRepository.save(found);
        }
        return ratingRepository.save(rating);
    }

    @Override
    public Double getAvgRating(String eventId) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("eventId").is(eventId)),
            Aggregation.group("eventId").avg("rating").as("avgRating"));

        AggregationResults<AvgRatingResult> result = mongoTemplate.aggregate(aggregation, "Rating", AvgRatingResult.class);
        return result.getMappedResults().get(0).getAvgRating();
    }
}
