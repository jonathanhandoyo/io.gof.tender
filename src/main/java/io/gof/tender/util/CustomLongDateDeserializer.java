package io.gof.tender.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomLongDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        String dateStr = jsonParser.getText();
        if (StringUtils.isNotBlank(dateStr)) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}