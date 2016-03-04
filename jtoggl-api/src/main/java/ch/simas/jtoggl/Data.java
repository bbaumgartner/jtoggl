package ch.simas.jtoggl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hpa on 4.3.16.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
