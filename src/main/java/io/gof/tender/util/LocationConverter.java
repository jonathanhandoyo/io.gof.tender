package io.gof.tender.util;

import io.gof.tender.domain.Location;
import org.neo4j.ogm.typeconversion.AttributeConverter;

public class LocationConverter implements AttributeConverter<Location, String> {
    @Override
    public String toGraphProperty(Location value) {
        return value != null ? value.toString() : null;
    }

    @Override
    public Location toEntityAttribute(String value) {
        return Location.fromJson(value);
    }
}
