package io.gof.tender.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gof.tender.domain.Milestone;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.io.IOException;

public class AlbumConverter implements AttributeConverter<Milestone.Album, String> {

    @Override
    public String toGraphProperty(Milestone.Album album) {
        try {
            return album != null ? new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(album) : null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Milestone.Album toEntityAttribute(String string) {
        try {
            return string != null ? new ObjectMapper().readerFor(Milestone.Album.class).readValue(string) : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
