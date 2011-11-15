package ch.simas.jtoggl;

import ch.simas.jtoggl.util.DateUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JToggl {

    public static final String DATA = "data";
    public static final String PLACEHOLDER = "{0}";
    private final static String TIME_ENTRIES = "https://www.toggl.com/api/v6/time_entries.json";
    private final static String TIME_ENTRY = "https://www.toggl.com/api/v6/time_entries/{0}.json";
    private final static String WORKSPACES = "https://www.toggl.com/api/v6/workspaces.json";
    private final static String CLIENTS = "https://www.toggl.com/api/v6/clients.json";
    private final static String CLIENT = "https://www.toggl.com/api/v6/clients/{0}.json";
    private final static String PROJECTS = "https://www.toggl.com/api/v6/projects.json";
    private final static String PROJECT = "https://www.toggl.com/api/v6/projects/{0}.json";
    private final static String TASKS = "https://www.toggl.com/api/v6/tasks.json";
    private final static String TAGS = "https://www.toggl.com/api/v6/tags.json";
    private String user;
    private String password;

    public JToggl(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public List<TimeEntry> getTimeEntries() {
        return this.getTimeEntries(null, null);
    }

    public List<TimeEntry> getTimeEntries(Date startDate, Date endDate) {
        Client client = prepareClient();
        WebResource webResource = client.resource(TIME_ENTRIES);

        if (startDate != null && endDate != null) {
            MultivaluedMap queryParams = new MultivaluedMapImpl();
            queryParams.add("start_date", DateUtil.convertDateToString(startDate));
            queryParams.add("end_date", DateUtil.convertDateToString(endDate));
            webResource = webResource.queryParams(queryParams);
        }
        String response = webResource.get(String.class);
        JSONObject object = (JSONObject) JSONValue.parse(response);
        JSONArray data = (JSONArray) object.get(DATA);

        List<TimeEntry> entries = new ArrayList<TimeEntry>();
        for (Object obj : data) {
            JSONObject entryObject = (JSONObject) obj;
            entries.add(new TimeEntry(entryObject.toJSONString()));
        }
        return entries;
    }

    public TimeEntry getTimeEntry(Long id) {
        Client client = prepareClient();
        String url = TIME_ENTRY.replace(PLACEHOLDER, id.toString());
        WebResource webResource = client.resource(url);

        String response = webResource.get(String.class);

        JSONObject object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);

        return new TimeEntry(data.toJSONString());
    }

    public TimeEntry createTimeEntry(TimeEntry timeEntry) {
        Client client = prepareClient();
        WebResource webResource = client.resource(TIME_ENTRIES);

        JSONObject object = createTimeEntryRequestParameter(timeEntry);
        String response = webResource.entity(
                object.toJSONString(), MediaType.APPLICATION_JSON_TYPE).post(String.class);

        object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);
        return new TimeEntry(data.toJSONString());
    }

    public TimeEntry updateTimeEntry(TimeEntry timeEntry) {
        Client client = prepareClient();
        String url = TIME_ENTRY.replace(PLACEHOLDER, timeEntry.getId().toString());
        WebResource webResource = client.resource(url);

        JSONObject object = createTimeEntryRequestParameter(timeEntry);
        String response = webResource.entity(
                object.toJSONString(), MediaType.APPLICATION_JSON_TYPE).put(String.class);

        object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);
        return new TimeEntry(data.toJSONString());
    }

    public void destroyTimeEntry(Long id) {
        Client client = prepareClient();
        String url = TIME_ENTRY.replace(PLACEHOLDER, id.toString());
        WebResource webResource = client.resource(url);

        webResource.delete(String.class);
    }

    public List<Workspace> getWorkspaces() {
        Client client = prepareClient();
        WebResource webResource = client.resource(WORKSPACES);

        String response = webResource.get(String.class);
        JSONObject object = (JSONObject) JSONValue.parse(response);
        JSONArray data = (JSONArray) object.get(DATA);

        List<Workspace> workspaces = new ArrayList<Workspace>();
        for (Object obj : data) {
            JSONObject entryObject = (JSONObject) obj;
            workspaces.add(new Workspace(entryObject.toJSONString()));
        }
        return workspaces;
    }

    public List<ch.simas.jtoggl.Client> getClients() {
        Client client = prepareClient();
        WebResource webResource = client.resource(CLIENTS);

        String response = webResource.get(String.class);
        JSONObject object = (JSONObject) JSONValue.parse(response);
        JSONArray data = (JSONArray) object.get(DATA);

        List<ch.simas.jtoggl.Client> clients = new ArrayList<ch.simas.jtoggl.Client>();
        for (Object obj : data) {
            JSONObject entryObject = (JSONObject) obj;
            clients.add(new ch.simas.jtoggl.Client(entryObject.toJSONString()));
        }
        return clients;
    }

    public ch.simas.jtoggl.Client createClient(ch.simas.jtoggl.Client clientObject) {
        Client client = prepareClient();
        WebResource webResource = client.resource(CLIENTS);

        JSONObject object = createClientRequestParameter(clientObject);
        String response = webResource.entity(
                object.toJSONString(), MediaType.APPLICATION_JSON_TYPE).post(String.class);

        object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);
        return new ch.simas.jtoggl.Client(data.toJSONString());
    }

    public ch.simas.jtoggl.Client updateClient(ch.simas.jtoggl.Client clientObject) {
        Client client = prepareClient();
        String url = CLIENT.replace(PLACEHOLDER, clientObject.getId().toString());
        WebResource webResource = client.resource(url);

        JSONObject object = createClientRequestParameter(clientObject);
        String response = webResource.entity(
                object.toJSONString(), MediaType.APPLICATION_JSON_TYPE).put(String.class);

        object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);
        return new ch.simas.jtoggl.Client(data.toJSONString());
    }

    public void destroyClient(Long id) {
        Client client = prepareClient();
        String url = CLIENT.replace(PLACEHOLDER, id.toString());
        WebResource webResource = client.resource(url);

        webResource.delete(String.class);
    }

    public List<Project> getProjects() {
        Client client = prepareClient();
        WebResource webResource = client.resource(PROJECTS);

        String response = webResource.get(String.class);
        JSONObject object = (JSONObject) JSONValue.parse(response);
        JSONArray data = (JSONArray) object.get(DATA);

        List<Project> projects = new ArrayList<Project>();
        for (Object obj : data) {
            JSONObject entryObject = (JSONObject) obj;
            projects.add(new Project(entryObject.toJSONString()));
        }
        return projects;
    }

    public Project createProject(Project project) {
        Client client = prepareClient();
        WebResource webResource = client.resource(PROJECTS);

        JSONObject object = createProjectRequestParameter(project);
        String response = webResource.entity(
                object.toJSONString(), MediaType.APPLICATION_JSON_TYPE).post(String.class);

        object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);
        return new Project(data.toJSONString());
    }

    public Project updateProject(Project project) {
        Client client = prepareClient();
        String url = PROJECT.replace(PLACEHOLDER, project.getId().toString());
        WebResource webResource = client.resource(url);

        JSONObject object = createProjectRequestParameter(project);
        String response = webResource.entity(
                object.toJSONString(), MediaType.APPLICATION_JSON_TYPE).put(String.class);

        object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);
        return new Project(data.toJSONString());
    }

    public ProjectUser createProjectUser(ProjectUser projectUser) {
        throw new UnsupportedOperationException();
    }

    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    public Task createTask(Task task) {
        throw new UnsupportedOperationException();
    }

    public Task udpateTask(Task task) {
        throw new UnsupportedOperationException();
    }

    public Task destroyTask(Task task) {
        throw new UnsupportedOperationException();
    }

    public List<Tag> getTags() {
        throw new UnsupportedOperationException();
    }

    public User getCurrentUser() {
        throw new UnsupportedOperationException();
    }

    public User getCurrentUserWithRelatedData() {
        throw new UnsupportedOperationException();
    }

    private Client prepareClient() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(user, password));
        client.addFilter(new LoggingFilter());
        return client;
    }

    private JSONObject createTimeEntryRequestParameter(TimeEntry timeEntry) {
        JSONObject object = new JSONObject();
        object.put("time_entry", timeEntry.toJSONObject());
        return object;
    }

    private JSONObject createClientRequestParameter(ch.simas.jtoggl.Client client) {
        JSONObject object = new JSONObject();
        object.put("client", client.toJSONObject());
        return object;
    }

    private JSONObject createProjectRequestParameter(Project project) {
        JSONObject object = new JSONObject();
        object.put("project", project.toJSONObject());
        return object;
    }
}
