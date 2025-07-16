package demo.com.security.repository;

import demo.com.security.entity.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository
    extends MongoRepository<Tweet, Long> {
}
