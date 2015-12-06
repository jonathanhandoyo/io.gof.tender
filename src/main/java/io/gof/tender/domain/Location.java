package io.gof.tender.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "locations")
public class Location extends BaseEntity {
    @Id
    private String id;

    @DBRef
    private Project project;

    private String name;

    private double[] coordinate;
    private GeoJsonPoint point;
    private GeoJsonMultiPoint multiPoint;

}
