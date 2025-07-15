package demo.com.security.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "user_entity")
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String document;
    private String password;
    private Timestamp created;
    private Timestamp updated;
}
