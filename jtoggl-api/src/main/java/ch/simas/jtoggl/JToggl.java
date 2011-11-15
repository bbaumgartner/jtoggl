package ch.simas.jtoggl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JToggl {

    private final static String GET_TIME_ENTRIES = "https://www.toggl.com/api/v6/time_entries.json";
    private String user;
    private String password;

    public JToggl(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public TimeEntries getTimeEntries() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(user, password));
        WebResource webResource = client.resource(GET_TIME_ENTRIES);
        String response = webResource.get(String.class);

        TimeEntries entries = new TimeEntries();
        JSONObject obj = (JSONObject) JSONValue.parse(response);
        entries.setRelated_data_updated_at(this.convertStringToDate((String) obj.get("related_data_updated_at")));

        JSONArray data = (JSONArray) obj.get("data");
        for (Object objectEntry : data) {
            TimeEntry timeEntry = this.convertIntoTimeEntry(objectEntry);
            entries.getEntries().add(timeEntry);
        }

        return entries;
    }

    private TimeEntry convertIntoTimeEntry(Object objectEntry) {
        TimeEntry timeEntry = new TimeEntry();
        JSONObject object = (JSONObject) objectEntry;
        timeEntry.setId((Long) object.get("id"));
        timeEntry.setDescription((String) object.get("description"));
        timeEntry.setStart(this.convertStringToDate((String) object.get("start")));
        timeEntry.setStop(this.convertStringToDate((String) object.get("stop")));
        timeEntry.setDuration((Long) object.get("duration"));
        timeEntry.setBillable((Boolean) object.get("billable"));
        timeEntry.setIgnore_start_and_stop((Boolean) object.get("ignore_start_and_stop"));
        // Workspace
        JSONObject workspaceObject = (JSONObject) object.get("workspace");
        Workspace workspace = new Workspace();
        workspace.setId((Long) workspaceObject.get("id"));
        workspace.setName((String) workspaceObject.get("name"));
        timeEntry.setWorkspace(workspace);
        // Project
        JSONObject projectObject = (JSONObject) object.get("project");
        if (projectObject != null) {
            Project project = new Project();
            project.setId((Long) projectObject.get("id"));
            project.setName((String) projectObject.get("name"));
            project.setClient_project_name((String) projectObject.get("client_project_name"));
        }
        // Tag names
        JSONArray tagsArray = (JSONArray) object.get("tag_names");
        List<String> tags = new ArrayList<String>();
        for (Object arrayObject : tagsArray) {
            tags.add((String) arrayObject);
        }
        timeEntry.setTag_names(tags);

        return timeEntry;
    }

    private Date convertStringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(TimeEntries.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }
}
