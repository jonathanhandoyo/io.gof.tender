package io.gof.tender.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "messages")
public class Message extends BaseEntity {
    @Id
    private String id;

    private Date timestamp;
    private String username;
    private String content;
}
