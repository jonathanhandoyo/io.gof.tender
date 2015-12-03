package io.gof.tender.domain;

import io.gof.tender.util.DocumentConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.Set;

@Getter
@Setter
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
public class Vendor extends BaseEntity {
    @NotEmpty
    private String businessRegistrationId;

    @NotEmpty
    private String name;

    @Convert(DocumentConverter.class)
    private Document[] documents;

    @Relationship(type = "HAS_REPRESENTATIVE")
    private Set<Representative> representatives;
}
