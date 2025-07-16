package demo.com.security.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @MongoId
    @Indexed(name = "user_id")
    private String userId;

    @Indexed(name = "name")
    private String name;

    @Indexed(name = "password")
    private String password;

    @DBRef(lazy = true)
    @Indexed(name = "roles")
    private Set<Role> roles;
}
