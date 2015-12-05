package io.gof.tender.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "locations")
public class Location extends BaseEntity {
    @Id
    private String id;

    private Long projectId;
    private String name;

    private double[] coordinate;
    private GeoJsonPoint point;
    private GeoJsonMultiPoint multiPoint;

}
