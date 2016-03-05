package ch.simas.jtoggl;

import com.fasterxml.jackson.core.*;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Created by hpa on 5.3.16.
 */
public class CustomDateDeserializerTest {

    @Test
    public void testDeserialize() throws Exception {
        CustomDateDeserializer d = new CustomDateDeserializer();
        Calendar c = Calendar.getInstance();

        c.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
        c.set(2016, Calendar.FEBRUARY, 11, 10, 28, 43);
        c.set(Calendar.MILLISECOND, 464);
        assertTrue("No matching dates", equalDates(c, d.deserialize(getJsonParser("2016-02-11T10:28:43.464+01:00"), null)));

        c.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        c.set(2016, Calendar.FEBRUARY, 11, 10, 28, 43);
        c.set(Calendar.MILLISECOND, 464);
        assertTrue("No matching dates", equalDates(c, d.deserialize(getJsonParser("2016-02-11T10:28:43.464Z"), null)));

        c.setTimeZone(TimeZone.getTimeZone("GMT-1:00"));
        c.set(2016, Calendar.FEBRUARY, 11, 10, 28, 43);
        c.set(Calendar.MILLISECOND, 464);
        assertTrue("No matching dates", equalDates(c, d.deserialize(getJsonParser("2016-02-11T10:28:43.464-01:00"), null)));

        c.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
        c.set(2016, Calendar.FEBRUARY, 11, 10, 28, 43);
        c.set(Calendar.MILLISECOND, 464);
        assertTrue("No matching dates", equalDates(c, d.deserialize(getJsonParser("2016-02-11T10:28:43.464+03"), null)));

        c.setTimeZone(TimeZone.getTimeZone("GMT-4:30"));
        c.set(2016, Calendar.FEBRUARY, 11, 10, 28, 43);
        c.set(Calendar.MILLISECOND, 464);
        assertTrue("No matching dates", equalDates(c, d.deserialize(getJsonParser("2016-02-11T10:28:43.464-0430"), null)));

        c.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
        c.set(2016, Calendar.FEBRUARY, 11, 10, 28, 43);
        c.set(Calendar.MILLISECOND, 464);
        assertFalse("No matching dates", equalDates(c, d.deserialize(getJsonParser("2016-02-11T10:28:43.464-01:00"), null)));

    }

    private boolean equalDates(Calendar a, Calendar b) {

        int[] fields = new int[]{Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND, Calendar.ZONE_OFFSET};

        for (int f : fields) {
            if (a.get(f) != b.get(f))
                return false;
        }
        return true;
    }

    private JsonParser getJsonParser(final String value) {
        return new JsonParser() {
            @Override
            public ObjectCodec getCodec() {
                return null;

            }

            @Override
            public void setCodec(ObjectCodec c) {

            }

            @Override
            public Version version() {
                return null;
            }

            @Override
            public void close() throws IOException {

            }

            @Override
            public JsonToken nextToken() throws IOException, JsonParseException {
                return null;
            }

            @Override
            public JsonToken nextValue() throws IOException, JsonParseException {
                return null;
            }

            @Override
            public JsonParser skipChildren() throws IOException, JsonParseException {
                return null;
            }

            @Override
            public boolean isClosed() {
                return false;
            }

            @Override
            public JsonToken getCurrentToken() {
                return null;
            }

            @Override
            public int getCurrentTokenId() {
                return 0;
            }

            @Override
            public boolean hasCurrentToken() {
                return false;
            }

            @Override
            public boolean hasTokenId(int id) {
                return false;
            }

            @Override
            public String getCurrentName() throws IOException {
                return null;
            }

            @Override
            public JsonStreamContext getParsingContext() {
                return null;
            }

            @Override
            public JsonLocation getTokenLocation() {
                return null;
            }

            @Override
            public JsonLocation getCurrentLocation() {
                return null;
            }

            @Override
            public void clearCurrentToken() {

            }

            @Override
            public JsonToken getLastClearedToken() {
                return null;
            }

            @Override
            public void overrideCurrentName(String name) {

            }

            @Override
            public String getText() throws IOException {
                return value;
            }

            @Override
            public char[] getTextCharacters() throws IOException {
                return new char[0];
            }

            @Override
            public int getTextLength() throws IOException {
                return 0;
            }

            @Override
            public int getTextOffset() throws IOException {
                return 0;
            }

            @Override
            public boolean hasTextCharacters() {
                return false;
            }

            @Override
            public Number getNumberValue() throws IOException {
                return null;
            }

            @Override
            public NumberType getNumberType() throws IOException {
                return null;
            }

            @Override
            public int getIntValue() throws IOException {
                return 0;
            }

            @Override
            public long getLongValue() throws IOException {
                return 0;
            }

            @Override
            public BigInteger getBigIntegerValue() throws IOException {
                return null;
            }

            @Override
            public float getFloatValue() throws IOException {
                return 0;
            }

            @Override
            public double getDoubleValue() throws IOException {
                return 0;
            }

            @Override
            public BigDecimal getDecimalValue() throws IOException {
                return null;
            }

            @Override
            public Object getEmbeddedObject() throws IOException {
                return null;
            }

            @Override
            public byte[] getBinaryValue(Base64Variant bv) throws IOException {
                return new byte[0];
            }

            @Override
            public String getValueAsString(String def) throws IOException {
                return null;
            }
        };
    }

}