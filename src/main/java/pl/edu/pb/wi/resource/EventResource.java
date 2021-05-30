package pl.edu.pb.wi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import pl.edu.pb.wi.model.db.Event;
import pl.edu.pb.wi.service.EventService;
import pl.edu.pb.wi.service.PDFCreator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

@Path("/event")
public class EventResource {

    @Autowired
    private EventService eventService;

    @Autowired
    private PDFCreator pdfCreator;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAll() {
        return eventService.getAll();
    }

    //TODO NOT FOUND zaimplemetowac
    @GET
    @Path("/{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Event getById(@PathParam("eventId") String eventId) {
        return eventService.getById(eventId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Event createEvent(Event event) {//TODO event.date jako string
        return eventService.create(event);
    }

    @PUT
    @Path("/{eventId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Event updateEvent(@PathParam("eventId") String eventId, Event event) {
        return eventService.update(eventId, event);
    }

    @DELETE
    @Path("/{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEvent(@PathParam("eventId") String eventId) {
        Event event = eventService.getById(eventId);
        if (event != null) {
            eventService.delete(event);
            return Response.status(HttpStatus.OK.value())
                    .entity(String.format("Event('%s') zostal usuniety", eventId))
                    .build();
        }
        return Response.status(HttpStatus.NOT_FOUND.value())
                .entity(String.format("Event('%s') nie zostal znaleziony", eventId))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getEventsByWeekOrDate(@QueryParam("week") Integer week,
                                             @QueryParam("date") String dateStr) {
        if (week != null) {
            return eventService.getEventsByWeek(week);
        }
        return eventService.getEventsByDate(dateStr);
    }

    @GET
    @Path("/to-pdf")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generateEventsListPDF() {
        List<Event> eventList = eventService.getAll();
        File pdf = pdfCreator.create(eventList);
        Response.ResponseBuilder response = Response.ok(pdf, MediaType.APPLICATION_OCTET_STREAM);
        response.header("Content-Disposition", String.format("attachment; filename=\"%s\"", pdf.getName()));
        return response.build();
    }
}
