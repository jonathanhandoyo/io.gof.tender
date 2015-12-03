package io.gof.tender.domain;

import io.gof.tender.util.AlbumConverter;
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
    @DateString("yyyy-MM-dd HH:mi:ss")
    private Date timestamp;

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
        Map<String, Object>[] items;
    }
}
