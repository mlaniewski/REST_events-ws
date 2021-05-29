package pl.edu.pb.wi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pb.wi.model.db.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUconst(String uconst);
}
