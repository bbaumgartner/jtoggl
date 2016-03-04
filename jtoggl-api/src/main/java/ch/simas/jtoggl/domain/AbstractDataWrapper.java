package ch.simas.jtoggl.domain;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hpa on 4.3.16.
 */
@XmlRootElement(name = "data")
public abstract class AbstractDataWrapper<T>  {

    @XmlAnyElement(lax = true)
    @XmlMixed
    protected T data;

    protected T getData() {
        return data;
    }

    protected void setData(T data) {
        this.data = data;
    }
}
