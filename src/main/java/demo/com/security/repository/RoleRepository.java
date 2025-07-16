package demo.com.security.repository;

import demo.com.security.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository
    extends MongoRepository<Role, String> {
}
