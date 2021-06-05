package pl.edu.pb.wi.filter;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pb.wi.model.db.User;
import pl.edu.pb.wi.service.impl.AuthorizationServiceImpl;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

//@Provider
public class AuthorizationRequestFilter implements ContainerRequestFilter {

    @Autowired
    private AuthorizationServiceImpl authorizationService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        UriInfo uriInfo = requestContext.getUriInfo();
        if (uriInfo.getPath().equals("user/register") ||
                uriInfo.getPath().equals("event/all") ||
                uriInfo.getPath().equals("event") ||
                (uriInfo.getPath().matches("event/.+/rating") && requestContext.getMethod().equals(HttpMethod.GET))) { //filtr nie jest wymagany przy rejestracji, przy pobieraniu wszystkich eventow i przy filtrach
            return;
        }
        final String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);

            User user = authorizationService.authorize(values[0], values[1]);
            if (user == null) {
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity("Nieprawidłowa nazwa użytkownika lub hasło")
                                .build());
            } else {
                requestContext.getHeaders().add("userId", user.getUconst());
            }
        } else {
            requestContext.abortWith(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Brak authoryzacji")
                            .build());
        }

    }
}
