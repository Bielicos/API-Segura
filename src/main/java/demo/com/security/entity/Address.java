package demo.com.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "adress_entity")
public class Address {
    @Id
    private String id;
    private String userId;
    private String street;
    private String city;
    private String state;
    private String cep;
}
