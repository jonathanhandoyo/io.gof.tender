package io.gof.tender.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "revisions")
public class Revision extends BaseEntity {
    @Id
    private String id;

    private String number;
    private String description;
    private Long delta;
    private String[] milestones;

}
