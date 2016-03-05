package ch.simas.jtoggl.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by hpa on 5.3.16.
 */
public class CustomDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            return IData.INPUT_DATE_FORMAT.parse(p.getText());
        } catch (ParseException e) {
            return null;
        }
    }
}
