package io.gof.tender.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "users")
public class User extends BaseEntity {
    @Id
    private String id;
    private String icType;
    private String icNumber;

    private String username;
    private String pwdHashed;
    private String avatar64;

    private String name;
    private String email;

}
