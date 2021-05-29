package pl.edu.pb.wi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pb.wi.model.db.Rating;

public interface RatingRepository extends MongoRepository<Rating, String> {
}
