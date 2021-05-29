package pl.edu.pb.wi.service;

import pl.edu.pb.wi.model.db.User;

public interface AuthorizationService {
    User authorize(String username, String password);
}
