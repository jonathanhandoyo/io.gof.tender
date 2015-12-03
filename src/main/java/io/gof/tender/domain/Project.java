package io.gof.tender.domain;

import io.gof.tender.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseEntity {
    @NotEmpty
    private String name;

    @NotEmpty
    private Status status;

    private String category;
    private String description;

    @Convert(MethodologyConverter.class)
    private Methodology methodology;

    @Convert(ValuationConverter.class)
    private Valuation valuation;

    @Convert(ContractConverter.class)
    private Contract contract;

    @Convert(DocumentConverter.class)
    private Document[] documents;

    private String budgetReference;
    private String businessQualification;

    @Convert(MapConverter.class)
    private Map<String, Double> weight;

    @Convert(LocationConverter.class)
    private Location location;

    @Convert(RequirementConverter.class)
    private Requirement requirement;

    @Relationship(type = "ORGANIZED_BY")
    private Organizer organizer;

    @Relationship(type = "APPOINTED_TO")
    private Vendor vendor;

    @Relationship(type = "HAS_BIDDING")
    private Set<Bid> bids;

    @Relationship(type = "MILESTONE")
    private Set<Milestone> milestones;

    @Relationship(type = "HAS_COMMENTS")
    private Set<Comment> comments;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Methodology {
        private String documentation;
        private String evaluation;
        private String procurement;
        private String qualification;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Contract {
        private String paymentMethod;
        private String fiscalYearImposition;
        private String fundingSource;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Valuation {
        private Double ceiling;
        private Double estimate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Requirement {
        private Permit[] permits;
        private String[] items;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Permit {
            private String type;
            private String classification;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETE,
        ;
    }
}
