package io.gof.tender.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gof.tender.util.CustomLongDateDeserializer;
import io.gof.tender.util.CustomLongDateSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Builder
@Document(collection = "comments")
public class Comment extends BaseEntity {
    @Id
    private String id;

    @JsonSerialize(using = CustomLongDateSerializer.class)
    @JsonDeserialize(using = CustomLongDateDeserializer.class)
    private Date timestamp;

    private String username;
    private String content;
}
