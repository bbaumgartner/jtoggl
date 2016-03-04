package ch.simas.jtoggl.domain.request;

import ch.simas.jtoggl.domain.ProjectClient;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by vranikp on 4.3.16.
 *
 * @author vranikp
 */
@XmlRootElement(name = "client")
public class RequestClient {
    private ProjectClient client;

    public RequestClient() {
    }

    public RequestClient(ProjectClient clientObject) {
        client = clientObject.clone();
        if (client.getWid() == null && client.getWorkspace() != null) {
            client.setWid(client.getWorkspace().getId());
        }
        client.setWorkspace(null);
        client.setData(null);
    }

    public ProjectClient getClient() {
        return client;
    }

    public void setClient(ProjectClient client) {
        this.client = client;
    }

}
