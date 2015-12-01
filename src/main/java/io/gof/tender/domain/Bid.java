package io.gof.tender.domain;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Getter
@Setter
@NodeEntity
public class Bid extends BaseEntity {
    private boolean administrativeCompleteness;
    private boolean technicalCompleteness;
    private Double score;
    private Double price;
    private Double adjustedPrice;
    private boolean winningBid;
    private String reason;

    @Relationship(type = "REPRESENTED_BY")
    private Representative representative;

    @Relationship(type = "FROM")
    private Vendor vendor;
}
