package ch.simas.jtoggl;

import ch.simas.jtoggl.util.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by hpa on 5.3.16.
 */
public class CustomDateSerializer extends JsonSerializer<Calendar> {
    @Override
    public void serialize(Calendar value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        String result = DateUtil.formatDate(value);
        gen.writeString(result);
    }

}
