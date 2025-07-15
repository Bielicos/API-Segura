package demo.com.security.repository;

import demo.com.security.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdressRepository
    extends MongoRepository<Address, String> {
}
