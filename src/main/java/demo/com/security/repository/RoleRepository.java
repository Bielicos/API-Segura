package demo.com.security.repository;

import demo.com.security.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository
    extends MongoRepository<Role, Long> {
}
