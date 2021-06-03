package pl.edu.pb.wi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pb.wi.model.db.Event;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {
    Optional<Event> findByEconst(String econst);

    List<Event> findByWeek(Integer week);

    List<Event> findByMonthAndYear(Integer month, Integer year);

    List<Event> findByDateBetween(Date midnight, Date nextMidnight);
}
