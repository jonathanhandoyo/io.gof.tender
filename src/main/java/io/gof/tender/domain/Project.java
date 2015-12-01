package io.gof.tender.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.gof.tender.util.LocationConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.Set;

@Getter
@Setter
@NodeEntity
public class Project extends BaseEntity {
    @NotEmpty
    private String name;

    @NotEmpty
    private Status status;

    private String category;
    private String description;
    private String budgetReference;
    private Double value;
    private Double estimatedValue;
    private String[] requirements;

    private BusinessQualification businessQualification;
    private DocumentationMethod documentationMethod;
    private EvaluationMethod evaluationMethod;
    private FiscalYearImposition fiscalYearImposition;
    private FundingSource fundingSource;
    private PaymentMethod paymentMethod;
    private ProcurementMethod procurementMethod;
    private QualificationMethod qualificationMethod;

    @Convert(LocationConverter.class)
    private Location location;

    @Relationship(type = "ORGANIZED_BY")
    private Organizer organizer;

    @Relationship(type = "APPOINTED_TO")
    private Vendor vendor;

    @Relationship(type = "HAS_BIDDING")
    private Set<Bid> bids;

    @Getter
    @AllArgsConstructor
    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETE,
        ;
    }

    @Getter
    @AllArgsConstructor
    public enum ProcurementMethod {
        AUCTION ("Simple e-Auction"),
        SELECTION ("Simple e-Selection")
        ;

        private String display;
    }

    @Getter
    @AllArgsConstructor
    public enum DocumentationMethod {
        SINGLE ("Single File"),
        ;

        private String display;
    }

    @Getter
    @AllArgsConstructor
    public enum QualificationMethod {
        POST ("Post Qualification"),
        ;

        private String display;
    }

    @Getter
    @AllArgsConstructor
    public enum EvaluationMethod {
        BUDGET_CEILING ("Budget Ceiling"),
        KNOCKOUT ("Knockout"),
        ;

        private String display;
    }

    @Getter
    @AllArgsConstructor
    public enum PaymentMethod {
        LUMP_SUM ("Lump Sum"),
        UNIT_PRICE ("Unit Price"),
        ;

        private String display;
    }

    @Getter
    @AllArgsConstructor
    public enum FiscalYearImposition {
        SINGLE ("Single Year"),
        UNIT_PRICE ("Unit Price"),
        ;

        private String display;
    }

    @Getter
    @AllArgsConstructor
    public enum FundingSource {
        SINGLE ("Single Source"),
        ;

        private String display;
    }

    @Getter
    @AllArgsConstructor
    public enum BusinessQualification {
        SMALL ("Small Company"),
        SME ("Small and Medium Enterprise"),
        ;

        private String display;
    }
}
