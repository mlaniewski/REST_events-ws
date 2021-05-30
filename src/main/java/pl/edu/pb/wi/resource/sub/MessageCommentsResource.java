package pl.edu.pb.wi.resource.sub;

import pl.edu.pb.wi.model.Comment;
import pl.edu.pb.wi.service.impl.MessageService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("")
public class MessageCommentsResource {

    private MessageService service;

    private Long messageId;

    public MessageCommentsResource(MessageService service, Long messageId) {
        this.service = service;
        this.messageId = messageId;
    }

    @GET
    public List<Comment> getMessageComments() {
        return service.getMessage(messageId).getComments();
    }


}
