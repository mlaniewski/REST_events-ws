package pl.edu.pb.wi.service;

import pl.edu.pb.wi.model.db.Event;

import java.io.File;
import java.util.List;

public interface PDFCreator {
    File create(List<Event> eventList);
}
