package pl.edu.pb.wi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import pl.edu.pb.wi.model.db.Event;
import pl.edu.pb.wi.model.db.Rating;
import pl.edu.pb.wi.service.EventService;
import pl.edu.pb.wi.service.PDFCreator;
import pl.edu.pb.wi.service.RatingService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.util.List;

@Path("/event")
public class EventResource {

    @Autowired
    private EventService eventService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private PDFCreator pdfCreator;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAll() {
        return eventService.getAll();
    }

    @GET
    @Path("/{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("eventId") String eventId,
                            @Context UriInfo uriInfo) {
        Event event = eventService.getById(eventId);
        if (event == null) {
            return Response.status(HttpStatus.NOT_FOUND.value())
                    .entity(String.format("Event('%s') nie zostal znaleziony", eventId))
                    .build();
        }

        String uri = uriInfo.getBaseUriBuilder()
                .path(EventResource.class)
                .path(String.valueOf(event.getEconst()))
                .build()
                .toString();
        event.addLink(uri, "self");

        String uri2 = uriInfo.getBaseUriBuilder()
                .path(EventResource.class, "getEventRatings")
                .resolveTemplate("eventId", event.getEconst())
                .build()
                .toString();
        event.addLink(uri2, "ratings");

        return Response.status(HttpStatus.OK.value())
                .entity(event)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Event createEvent(Event event) {
        return eventService.create(event);
    }

    @PUT
    @Path("/{eventId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEvent(@PathParam("eventId") String eventId,
                                Event event,
                                @Context UriInfo uriInfo) {
        Event updated = eventService.update(eventId, event);
        if (updated == null) {
            return Response.status(HttpStatus.NOT_FOUND.value())
                    .entity(String.format("Event('%s') nie zostal znaleziony", eventId))
                    .build();
        }

        String uri = uriInfo.getBaseUriBuilder()
                .path(EventResource.class)
                .path(String.valueOf(event.getEconst()))
                .build()
                .toString();
        event.addLink(uri, "self");

        String uri2 = uriInfo.getBaseUriBuilder()
                .path(EventResource.class, "getEventRatings")
                .resolveTemplate("eventId", event.getEconst())
                .build()
                .toString();
        event.addLink(uri2, "ratings");

        return Response.status(HttpStatus.OK.value())
                .entity(event)
                .build();
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

    @GET
    @Path("/{eventId}/ratings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventRatings(@PathParam("eventId") String eventId) {
        Event event = eventService.getById(eventId);
        if (event == null) {
            return Response.status(HttpStatus.NOT_FOUND.value())
                    .entity(String.format("Event('%s') nie zostal znaleziony", eventId))
                    .build();
        }
        List<Rating> ratings = ratingService.getRatingsByEconst(event.getEconst());
        return Response.status(HttpStatus.OK.value())
                .entity(ratings)
                .build();
    }

    @POST
    @Path("/{eventId}/rating")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEventRating(@PathParam("eventId") String eventId,
                                            @HeaderParam("userId") String userId,
                                            Rating rating) {
        Event event = eventService.getById(eventId);
        if (event == null) {
            return Response.status(HttpStatus.NOT_FOUND.value())
                    .entity(String.format("Event('%s') nie zostal znaleziony", eventId))
                    .build();
        }
        rating.setUserId(userId);
        rating.setEventId(event.getEconst());
        Rating created = ratingService.createRating(rating);
        if (created == null) {
            return Response.status(HttpStatus.CONFLICT.value())
                    .build();
        }
        return Response.status(HttpStatus.OK.value())
                .entity(created)
                .build();
    }


    @GET
    @Path("/{eventId}/rating")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvgRating(@PathParam("eventId") String eventId) {
        Event event = eventService.getById(eventId);
        if (event == null) {
            return Response.status(HttpStatus.NOT_FOUND.value())
                    .entity(String.format("Event('%s') nie zostal znaleziony", eventId))
                    .build();
        }
        Double avgRating = ratingService.getAvgRating(eventId);
        return Response.status(HttpStatus.OK.value())
                .entity(avgRating)
                .build();
    }
}
