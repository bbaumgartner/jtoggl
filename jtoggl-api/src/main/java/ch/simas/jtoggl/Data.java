package ch.simas.jtoggl;

import javax.ws.rs.client.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hpa on 3.3.16.
 */
@XmlRootElement
public class Data {
    private Entity data;

    public Object getData() {
        return this;
    }

}
