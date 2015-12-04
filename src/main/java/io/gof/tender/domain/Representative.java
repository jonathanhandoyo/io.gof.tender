package io.gof.tender.domain;

import io.gof.tender.util.DocumentsConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

@Getter
@Setter
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
public class Representative extends User {
    @Relationship(type = "FROM_VENDOR")
    private Vendor vendor;

    @Convert(DocumentsConverter.class)
    private Document[] documents;
}
