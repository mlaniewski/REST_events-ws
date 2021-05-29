package pl.edu.pb.wi.service;

import pl.edu.pb.wi.model.db.Event;

import java.util.Date;
import java.util.List;

public interface EventService {
    List<Event> getAll();

    Event getById(String econst);

    Event create(Event event);

    Event update(String econst, Event event);

    void delete(Event event);

    List<Event> getEventsByWeek(Integer week);

    List<Event> getEventsByDate(Date date);
}
