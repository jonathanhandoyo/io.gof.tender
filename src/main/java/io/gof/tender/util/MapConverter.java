package io.gof.tender.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.io.IOException;
import java.util.Map;

public class MapConverter implements AttributeConverter<Map, String> {
    @Override
    public String toGraphProperty(Map value) {
        try {
            return value != null ? new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(value) : null;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public Map toEntityAttribute(String value) {
        try {
            return StringUtils.isNotBlank(value) ? new ObjectMapper().readerFor(Map.class).readValue(value) : null;
        } catch (IOException e) {
            return null;
        }
    }
}
