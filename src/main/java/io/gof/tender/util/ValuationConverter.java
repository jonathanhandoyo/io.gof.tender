package io.gof.tender.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gof.tender.domain.Project;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.io.IOException;

public class ValuationConverter implements AttributeConverter<Project.Valuation, String> {
    @Override
    public String toGraphProperty(Project.Valuation value) {
        try {
            return value != null ? new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(value) : null;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public Project.Valuation toEntityAttribute(String value) {
        try {
            return StringUtils.isNotBlank(value) ? new ObjectMapper().readerFor(Project.Valuation.class).readValue(value) : null;
        } catch (IOException e) {
            return null;
        }
    }
}
