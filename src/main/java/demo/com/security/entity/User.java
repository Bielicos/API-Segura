package demo.com.security.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_cl")
public class User {

    @Id
    private String userId;
    private String name;
    private String password;
    private Set<Role> roles;
}
