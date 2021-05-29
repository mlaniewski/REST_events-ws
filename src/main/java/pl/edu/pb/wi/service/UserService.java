package pl.edu.pb.wi.service;

import pl.edu.pb.wi.model.db.User;

public interface UserService {
    User findUserByUconst(String uconst);
    User register(User user);
}
