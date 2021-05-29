package pl.edu.pb.wi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pb.wi.model.Message;
import pl.edu.pb.wi.resource.subresource.MessageCommentsResource;
import pl.edu.pb.wi.service.impl.MessageService;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/messages")
@Singleton
public class MessageResource {

    @Autowired
    private MessageService service;

    @GET
    @Path("/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message getMessage(@PathParam("messageId") Long id,
                              @Context UriInfo uriInfo) {
        Message newMessage = service.getMessage(id);
        String uri = uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(String.valueOf(newMessage.getId()))
                .build()
                .toString();
        newMessage.addLink(uri, "self");

        String uri2 = uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(MessageResource.class, "getMessageComments")
                .path(MessageCommentsResource.class)
                .resolveTemplate("messageId", newMessage.getId())
                .build()
                .toString();
        newMessage.addLink(uri2, "comments");

        return newMessage;
    }

    @GET
    @Path("/{messageId}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public MessageCommentsResource getMessageComments(@PathParam("messageId") Long id) {
        return new MessageCommentsResource(service, id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getAllMessages() {
        return service.getAllMessages();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMessage(Message message,
                                 @Context UriInfo uriInfo) {
        Message newMessage = service.createMessage(message);
        String newId = String.valueOf(message.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(uri)
                .entity(newMessage)
                .build();
    }

    @PUT
    @Path("/{messageId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message updateMessage(@PathParam("messageId") Long id, Message message) {
        return service.updateMessage(id, message);
    }

    @DELETE
    @Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") Long id) {
        service.deleteMessage(id);
    }


}
