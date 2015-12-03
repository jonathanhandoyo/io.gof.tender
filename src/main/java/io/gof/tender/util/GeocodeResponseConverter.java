package io.gof.tender.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.geocoder.model.GeocodeResponse;
import io.gof.tender.domain.Milestone;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.io.IOException;

public class GeocodeResponseConverter implements AttributeConverter<GeocodeResponse, String> {

    @Override
    public String toGraphProperty(GeocodeResponse value) {
        try {
            return value != null ? new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(value) : null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GeocodeResponse toEntityAttribute(String string) {
        try {
            return string != null ? new ObjectMapper().readerFor(GeocodeResponse.class).readValue(string) : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
