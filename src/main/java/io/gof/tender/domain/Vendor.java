package io.gof.tender.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "vendors")
public class Vendor extends BaseEntity {
    @Id
    private String id;

    private String taxId;
    private String name;
    private String address;
}
