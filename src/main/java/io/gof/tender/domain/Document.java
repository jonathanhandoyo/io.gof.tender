package io.gof.tender.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;

@Getter
@Setter
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    private String contentType;
    private String category;
    private String title;
    private String filename;
    private String contentBase64;
}
