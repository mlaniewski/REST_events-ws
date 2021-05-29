package pl.edu.pb.wi.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import pl.edu.pb.wi.filter.AuthorizationRequestFilter;
import pl.edu.pb.wi.filter.LogingFilter;
import pl.edu.pb.wi.resource.UserResource;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(UserResource.class);

        register(LogingFilter.class);
        register(AuthorizationRequestFilter.class);
    }
}

