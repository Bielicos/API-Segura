package demo.com.security.repository;

import demo.com.security.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository
    extends MongoRepository<User, String> {
}
