package ch.simas.jtoggl;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.testng.AssertJUnit.assertEquals;


/**
 * Created by hpa on 5.3.16.
 */
public class CustomDateDeserializerTest {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Test(enabled = false)
    public void testDeserialize() throws Exception {
        CustomDateDeserializer d = new CustomDateDeserializer();

        assertEquals("No matching dates", DateTime.parse("2016-02-11T10:28:43.464+01:00"), d.deserialize(getJsonParser("2016-02-11T10:28:43.464+01:00"), null));

        assertEquals("No matching dates", DateTime.parse("2016-02-11T10:28:43.464Z"), d.deserialize(getJsonParser("2016-02-11T10:28:43.464Z"), null));
        assertEquals("No matching dates", DateTime.parse("2016-02-11T10:28:43.464-01:00"), d.deserialize(getJsonParser("2016-02-11T10:28:43.464-01:00"), null));
        assertEquals("No matching dates", DateTime.parse("2016-02-11T10:28:43.464+03:00"), d.deserialize(getJsonParser("2016-02-11T10:28:43.464+03"), null));
        assertEquals("No matching dates", DateTime.parse("2016-02-11T10:28:43.464-04:30"), d.deserialize(getJsonParser("2016-02-11T10:28:43.464-0430"), null));

        assertEquals("No matching dates", DateTime.parse("2016-02-11T10:28:43.464+01:00"), d.deserialize(getJsonParser("2016-02-11T10:28:43.464-01:00"), null));

    }

    @Test
    public void timezoneTest() throws ParseException {
        Date d = SIMPLE_DATE_FORMAT.parse("2016-02-11T10:28:43.464+0300");
        TimeZone gmt = TimeZone.getTimeZone("GMT+2");
        Calendar c = Calendar.getInstance(gmt);
        c.setTimeZone(gmt);
        c.set(Calendar.ZONE_OFFSET,3*3600000);
        c.setTime(d);
        c.setTimeZone(gmt);
        c=Calendar.getInstance();
        c.set(Calendar.ZONE_OFFSET,5*3600000);
        c.set(2016,1,24,13,24,56);
        //  c.set(Calendar.ZONE_OFFSET,2*3600000);

        System.out.println(SIMPLE_DATE_FORMAT.format(c.getTime()));
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