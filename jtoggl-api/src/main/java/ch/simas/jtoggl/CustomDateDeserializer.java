package ch.simas.jtoggl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hpa on 5.3.16.
 */
public class CustomDateDeserializer extends JsonDeserializer<Calendar> {
    @Override
    public Calendar deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Pattern pat = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})(?:.(\\d+))?(?:(Z)|([+-]\\d{2})(?::?(\\d{2}))?)?");
        String d;
        try {
            d = p.getText();
        } catch (Exception e) {
            d = p.getValueAsString();
        }
        // return OffsetDateTime.parse(d);
        Matcher m = pat.matcher(d);
        if (m.matches()) {
            int year = Integer.parseInt(m.group(1));
            int month = Integer.parseInt(m.group(2));
            int day = Integer.parseInt(m.group(3));
            int hour = Integer.parseInt(m.group(4));
            int minute = Integer.parseInt(m.group(5));
            int second = Integer.parseInt(m.group(6));
            int milisecond = m.group(7) == null ? 0 : Integer.parseInt(m.group(7));

            Calendar c = Calendar.getInstance();

            String tz = m.group(8);
            if (tz == null && m.group(9) != null) {
                int tzh = Integer.parseInt(m.group(9));
                int tzm = m.group(10) == null ? 0 : Integer.parseInt(m.group(10));
                int s = Integer.signum(tzh);
                tzh = Math.abs(tzh);
                c.set(Calendar.ZONE_OFFSET, ((tzh * 3600) + (tzm * 60)) * 1000 * s);
            } else {
                c.set(Calendar.ZONE_OFFSET, 0);
            }
            c.set(year, month - 1, day, hour, minute, second);
            c.set(Calendar.MILLISECOND, milisecond);
            return c;
        }
        //return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(d);
        throw new InvalidFormatException("Unable to parse date", p.getText(), Calendar.class);
    }
}
