package io.gof.tender.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gof.tender.util.CustomShortDateDeserializer;
import io.gof.tender.util.CustomShortDateSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Builder
@Document(collection = "posts")
public class Post {
    @Id
    private String id;

    private String projectId;

    @JsonSerialize(using = CustomShortDateSerializer.class)
    @JsonDeserialize(using = CustomShortDateDeserializer.class)
    private Date date;

    private String username;

    private String title;
    private String content;

    private Image image;

    @Getter
    @Setter
    @Builder
    public static class Image extends BaseEntity {
        private String thumbSource;
        private String thumbBase64;
        private String source;
        private String base64;
    }
}
