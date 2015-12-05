package io.gof.tender.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//udah
@Getter
@Setter
@Builder
@Document(collection = "users")
public class User extends BaseEntity {
    @Id
    private String id;

    private String username;
    private String password;
    private String avatar64;
}
