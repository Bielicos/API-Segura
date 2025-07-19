package demo.com.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.lang.classfile.TypeAnnotation;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "tweets")
public class Tweet {
    @MongoId
    @Indexed(name = "tweet_id")
    private String tweetId;

    @Indexed(name = "user")
    @DBRef(lazy = true)
    private User user;

    @Indexed(name = "content")
    private String content;

    @Indexed(name = "creationTimestamp")
    private Instant creationTimestamp;
}
