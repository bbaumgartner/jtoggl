package ch.simas.jtoggl.domain;

/**
 * Created by vranikp on 4.3.16.
 *
 * @author vranikp
 */
public interface IData<T> {

    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    T getData();

    void setData(T data);
}
