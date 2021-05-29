package pl.edu.pb.wi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pb.wi.model.db.Event;

public interface EventRepository extends MongoRepository<Event, String> {
}
