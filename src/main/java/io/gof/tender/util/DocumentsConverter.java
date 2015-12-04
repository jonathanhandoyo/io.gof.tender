package io.gof.tender.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gof.tender.domain.Bid;
import io.gof.tender.domain.Document;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.io.IOException;

public class DocumentsConverter implements AttributeConverter<Document[], String> {
    @Override
    public String toGraphProperty(Document[] value) {
        try {
            return value != null ? new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(value) : null;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public Document[] toEntityAttribute(String value) {
        try {
            return StringUtils.isNotBlank(value) ? new ObjectMapper().readerFor(Document[].class).readValue(value) : null;
        } catch (IOException e) {
            return null;
        }
    }
}
