package io.gof.tender.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.util.Map;

public class ArrayMapConverter implements AttributeConverter<Map[], String[]> {

    @Override
    public String[] toGraphProperty(Map[] maps) {
        try {
            if (maps != null) {
                String[] strings = ArrayUtils.EMPTY_STRING_ARRAY;

                ObjectMapper mapper = new ObjectMapper();
                for(Map map : maps){
                    ArrayUtils.add(strings, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
                }

                return strings;
            }else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map[] toEntityAttribute(String[] strings) {
        try {
            if (strings != null) {
                Map[] maps = new Map[0];

                ObjectMapper mapper = new ObjectMapper();
                for(String string : strings){
                    ArrayUtils.add(strings, mapper.readerFor(Map.class).readValue(string));
                }

                return maps;
            }else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
