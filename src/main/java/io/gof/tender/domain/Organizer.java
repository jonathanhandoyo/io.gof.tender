package io.gof.tender.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;

@Getter
@Setter
@NodeEntity
@AllArgsConstructor
public class Organizer extends BaseEntity {
    @NotEmpty
    private String agencyName;

    @NotEmpty
    private String workUnit;
}
