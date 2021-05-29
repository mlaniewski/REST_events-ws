package pl.edu.pb.wi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pb.wi.model.db.User;
import pl.edu.pb.wi.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public User login(@HeaderParam("userId") String userId) {
        return userService.findUserByUconst(userId);
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User register(User user) {
        return userService.register(user);
    }
}
