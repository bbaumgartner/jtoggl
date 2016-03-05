package ch.simas.jtoggl.domain;

import java.text.SimpleDateFormat;

/**
 * Created by vranikp on 4.3.16.
 *
 * @author vranikp
 */
public interface IData<T> {

    SimpleDateFormat OUTPUT_DATE_FORMAT =new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    SimpleDateFormat INPUT_DATE_FORMAT =new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    T getData();

    void setData(T data);
}
