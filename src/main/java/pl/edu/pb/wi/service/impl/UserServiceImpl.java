package pl.edu.pb.wi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.model.db.User;
import pl.edu.pb.wi.repository.UserRepository;
import pl.edu.pb.wi.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findUserByUconst(String uconst) {
        return userRepository.findByUconst(uconst).orElse(null);
    }

    @Override
    public User register(User user) {
        long count = userRepository.count();
        user.setUconst(String.format("us%d", count));
        return userRepository.save(user);
    }

}
