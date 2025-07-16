package demo.com.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.lang.classfile.TypeAnnotation;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "tweets")
public class Tweet {
    @MongoId
    @Indexed(name = "tweet_id")
    @Field(targetType = FieldType.DECIMAL128)
    private Long tweetId;

    @Indexed(name = "user")
    private User user;

    @Indexed(name = "content")
    private Integer content;

    @Indexed(name = "creationTimestamp")
    private Instant creationTimestamp;
}
