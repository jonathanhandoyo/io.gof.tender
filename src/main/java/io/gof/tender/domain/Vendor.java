package io.gof.tender.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;

@Getter
@Setter
@NodeEntity
public class Vendor extends BaseEntity {
    @NotEmpty
    private String businessRegistrationId;

    @NotEmpty
    private String name;
}
