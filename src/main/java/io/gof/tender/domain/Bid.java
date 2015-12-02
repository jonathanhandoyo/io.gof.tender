package io.gof.tender.domain;

import io.gof.tender.util.EvaluationConverter;
import io.gof.tender.util.PriceConverter;
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
public class Bid extends BaseEntity {
    @Convert(EvaluationConverter.class)
    private Evaluation evaluation;

    @Convert(PriceConverter.class)
    private Price price;

    private Double score;
    private String reason;

    @Relationship(type = "REPRESENTED_BY")
    private Representative representative;

    @Relationship(type = "FROM")
    private Vendor vendor;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Evaluation {
        private boolean administrative;
        private boolean technical;
        private boolean price;
        private boolean qualification;
        private boolean winner;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Price {
        private Double offered;
        private Double adjusted;
    }
}
