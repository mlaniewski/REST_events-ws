package pl.edu.pb.wi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pb.wi.model.db.Event;
import pl.edu.pb.wi.repository.EventRepository;
import pl.edu.pb.wi.service.EventService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event getById(String econst) {
        return eventRepository.findByEconst(econst).orElse(null);
    }

    @Override
    public Event create(Event event) {
        long count = eventRepository.count();
        event.setEconst(String.format("ev%d", count));
        event.setDate(new Date(event.getDate().getTime() + 2*60*1000));
        return eventRepository.save(event);
    }

    @Override
    public Event update(String econst, Event event) {
        Event found = getById(econst);
        if (found == null) {
            return null;
        }

        found.setDate(event.getDate());
        found.setDescription(event.getDescription());
        found.setName(event.getName());
        found.setType(event.getType());
        found.setWeek(event.getWeek());
        found.setMonth(event.getMonth());
        found.setYear(event.getYear());

        return eventRepository.save(found);
    }

    @Override
    public void delete(Event event) {
        eventRepository.delete(event);
    }

    @Override
    public List<Event> getEventsByWeek(Integer week) {
        return eventRepository.findByWeek(week);
    }

    @Override
    public List<Event> getEventsByDate(String dateStr) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            Date midnight = new Date(date.getTime());
            Date nextMidnight = new Date(date.getTime() + 24*60*60*1000 - 1);
            List<Event> events = eventRepository.findByDateBetween(midnight, nextMidnight);
            return events;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
