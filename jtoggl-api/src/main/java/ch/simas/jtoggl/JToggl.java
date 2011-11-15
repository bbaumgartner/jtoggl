package ch.simas.jtoggl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.Date;
import javax.ws.rs.core.MultivaluedMap;

public class JToggl {

    private final static String GET_TIME_ENTRIES = "https://www.toggl.com/api/v6/time_entries.json";
    private String user;
    private String password;

    public JToggl(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public TimeEntries getTimeEntries() {
        return this.getTimeEntries(null, null);
    }

    public TimeEntries getTimeEntries(Date startDate, Date endDate) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(user, password));
        client.addFilter(new LoggingFilter());

        WebResource webResource = client.resource(GET_TIME_ENTRIES);
        if (startDate != null && endDate != null) {
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            queryParams.add("start_date", DateUtil.convertDateToString(startDate));
            queryParams.add("end_date", DateUtil.convertDateToString(endDate));
            webResource = webResource.queryParams(queryParams);
        }
        String response = webResource.get(String.class);
        return new TimeEntries(response);
    }
}
