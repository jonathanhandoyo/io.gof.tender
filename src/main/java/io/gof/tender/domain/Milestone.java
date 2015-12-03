package io.gof.tender.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gof.tender.util.AlbumConverter;
import io.gof.tender.util.CustomShortDateDeserializer;
import io.gof.tender.util.CustomShortDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
public class Milestone extends BaseEntity {
    @JsonSerialize(using = CustomShortDateSerializer.class)
    @JsonDeserialize(using = CustomShortDateDeserializer.class)
    private Date due;

    private String title;
    private String content;

    @Convert(AlbumConverter.class)
    private Album album;

    private String[] highlights;
    private Integer score;
    private Integer scorePercentage;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Album {
        private Image[] images;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
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
