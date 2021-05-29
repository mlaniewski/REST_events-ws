package pl.edu.pb.wi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pb.wi.model.db.User;
import pl.edu.pb.wi.repository.UserRepository;
import pl.edu.pb.wi.service.AuthorizationService;

import java.util.Optional;

@Component
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User authorize(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        return user.orElse(null);
    }
}
