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
@Document(collection = "milestones")
public class Milestone {
    @Id
    private String id;

    @JsonSerialize(using = CustomShortDateSerializer.class)
    @JsonDeserialize(using = CustomShortDateDeserializer.class)
    private Date due;

    private String title;
    private String content;

    private Album album;

    private String[] highlights;

    @Getter
    @Setter
    @Builder
    public static class Album {
        private Image[] images;

        @Getter
        @Setter
        @Builder
        public static class Image {
            private String title;
            private String thumbSource;
            private String thumbBase64;
            private String source;
            private String base64;
            private String alt;
        }
    }
}
