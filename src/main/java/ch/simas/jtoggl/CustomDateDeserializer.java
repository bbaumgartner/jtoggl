package ch.simas.jtoggl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * Json deserializer for ISO 8601 formatted dates.
 */
public class CustomDateDeserializer extends JsonDeserializer<DateTime> {
    @Override
    public DateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        String d;
        try {
            d = p.getText();
        } catch (Exception e) {
            d = p.getValueAsString();
        }

        return ISODateTimeFormat.dateTimeParser().parseDateTime(d);


    }
}
