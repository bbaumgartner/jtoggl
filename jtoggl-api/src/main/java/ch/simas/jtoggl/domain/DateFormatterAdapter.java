package ch.simas.jtoggl.domain;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hpa on 5.3.16.
 */
public class DateFormatterAdapter extends XmlAdapter {
    @Override
    public Date unmarshal(Object v) throws Exception {
        if (v instanceof Date) {
            return (Date) v;
        } else if (v instanceof String) {
            return IData.INPUT_DATE_FORMAT.parse((String) v);
        }
        return null;
    }

    @Override
    public String marshal(Object v) throws Exception {
        if (v instanceof Date || v instanceof Calendar) {
            return IData.OUTPUT_DATE_FORMAT.format(v);
        }
        return null;
    }
}
