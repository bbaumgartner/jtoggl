/*
 * jtoggl - Java Wrapper for Toggl REST API https://www.toggl.com/public/api
 *
 * Copyright (C) 2011 by simas GmbH, Moosentli 7, 3235 Erlach, Switzerland
 * http://www.simas.ch
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.simas.jtoggl;

import ch.simas.jtoggl.util.DateUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
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

/**
 * API Class for Toggl REST API.
 * 
 * @author Simon Martinelli
 */
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
    private final static String TASK = "https://www.toggl.com/api/v6/tasks/{0}.json";
    private final static String TAGS = "https://www.toggl.com/api/v6/tags.json";
    private final static String PROJECT_USERS = "https://www.toggl.com/api/v6/workspaces/31366/project_users.json";
    private final static String GET_CURRENT_USER = "https://www.toggl.com/api/v6/me.json";
    private String user;
    private String password;

    /**
     * Constructor to create an instance of JToggl.
     * 
     * @param user username or api_token
     * @param password password or string "api_token"
     */
    public JToggl(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * Get latest time entries.
     * 
     * @return list of {@link TimeEntry}
     */
    public List<TimeEntry> getTimeEntries() {
        return this.getTimeEntries(null, null);
    }

    /**
     * Get time entries started in a specific time range. 
     * By default, the number of days from the field "How long are time entries 
     * visible in the timer" under "My settings" in Toggl is used to determine 
     * which time entries to return but you can specify another date range using 
     * start_date and end_date parameters.
     * 
     * @param startDate
     * @param endDate
     * @return list of {@link TimeEntry}
     */
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

    /**
     * Get a time entry.
     * 
     * @param id
     * @return TimeEntry or null if no Entry is found.
     */
    public TimeEntry getTimeEntry(Long id) {
        Client client = prepareClient();
        String url = TIME_ENTRY.replace(PLACEHOLDER, id.toString());
        WebResource webResource = client.resource(url);

        String response = null;
        try {
            response = webResource.get(String.class);
        } catch (UniformInterfaceException uniformInterfaceException) {
            return null;
        }

        JSONObject object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);

        return new TimeEntry(data.toJSONString());
    }

    /**
     * Create a new time entry.
     * 
     * @param timeEntry
     * @return created {@link TimeEntry}
     */
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

    /**
     * Update a time entry.
     * 
     * @param timeEntry
     * @return created {@link TimeEntry}
     */
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

    /**
     * Destroy a time entry.
     * 
     * @param id 
     */
    public void destroyTimeEntry(Long id) {
        Client client = prepareClient();
        String url = TIME_ENTRY.replace(PLACEHOLDER, id.toString());
        WebResource webResource = client.resource(url);

        webResource.delete(String.class);
    }

    /**
     * Get workspaces.
     * 
     * @return list of {@link Workspace}
     */
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

    /**
     * Get clients.
     * 
     * @return list of {@link ch.simas.jtoggl.Client}
     */
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

    /**
     * Create a new client.
     * 
     * @param clientObject
     * @return created {@link ch.simas.jtoggl.Client}
     */
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

    /**
     * Update a client.
     * 
     * @param clientObject
     * @return updated {@link ch.simas.jtoggl.Client}
     */
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

    /**
     * Destroy a client.
     * 
     * @param id 
     */
    public void destroyClient(Long id) {
        Client client = prepareClient();
        String url = CLIENT.replace(PLACEHOLDER, id.toString());
        WebResource webResource = client.resource(url);

        webResource.delete(String.class);
    }

    /**
     * Get projects.
     * 
     * @return list of {@link Project}
     */
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

    /**
     * Create a new project.
     * @param project
     * @return created {@link Project}
     */
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

    /**
     * Update a project.
     * 
     * @param project
     * @return updated {@link Project}
     */
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

    /**
     * Create a new project user.
     * 
     * @param projectUser
     * @return created {@link ProjectUser}
     */
    public ProjectUser createProjectUser(ProjectUser projectUser) {
        Client client = prepareClient();
        WebResource webResource = client.resource(PROJECT_USERS);

        JSONObject object = createProjectUserRequestParameter(projectUser);
        String response = webResource.entity(
                object.toJSONString(), MediaType.APPLICATION_JSON_TYPE).post(String.class);

        object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);
        return new ProjectUser(data.toJSONString());
    }

    /**
     * Get tasks
     * The user field may contain the users data who has been assigned with the task.
     * 
     * @return list of {@link Task}
     */
    public List<Task> getTasks() {
        Client client = prepareClient();
        WebResource webResource = client.resource(TASKS);

        String response = webResource.get(String.class);
        JSONObject object = (JSONObject) JSONValue.parse(response);
        JSONArray data = (JSONArray) object.get(DATA);

        List<Task> tasks = new ArrayList<Task>();
        for (Object obj : data) {
            JSONObject entryObject = (JSONObject) obj;
            tasks.add(new Task(entryObject.toJSONString()));
        }
        return tasks;
    }

    /** 
     * Create a new task.
     * 
     * @param task
     * @return created {@link Task}
     */
    public Task createTask(Task task) {
        Client client = prepareClient();
        WebResource webResource = client.resource(TASKS);

        JSONObject object = createTaskRequestParameter(task);
        String response = webResource.entity(
                object.toJSONString(), MediaType.APPLICATION_JSON_TYPE).post(String.class);

        object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);
        return new Task(data.toJSONString());
    }

    /**
     * Update a task.
     * 
     * @param task
     * @return updated {@link Task}
     */
    public Task updateTask(Task task) {
        Client client = prepareClient();
        String url = TASK.replace(PLACEHOLDER, task.getId().toString());
        WebResource webResource = client.resource(url);

        JSONObject object = createTaskRequestParameter(task);
        String response = webResource.entity(
                object.toJSONString(), MediaType.APPLICATION_JSON_TYPE).put(String.class);

        object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);
        return new Task(data.toJSONString());
    }

    /**
     * Destroy a task.
     * 
     * @param id
     */
    public void destroyTask(Long id) {
        Client client = prepareClient();
        String url = TASK.replace(PLACEHOLDER, id.toString());
        WebResource webResource = client.resource(url);

        webResource.delete(String.class);
    }

    /**
     * Get tags.
     * 
     * @return list of {@link Tag}
     */
    public List<Tag> getTags() {
        Client client = prepareClient();
        WebResource webResource = client.resource(TAGS);

        String response = webResource.get(String.class);
        JSONObject object = (JSONObject) JSONValue.parse(response);
        JSONArray data = (JSONArray) object.get(DATA);

        List<Tag> tags = new ArrayList<Tag>();
        for (Object obj : data) {
            JSONObject entryObject = (JSONObject) obj;
            tags.add(new Tag(entryObject.toJSONString()));
        }
        return tags;

    }

    /**
     * Get current user.
     * 
     * @return current user {@link User}
     */
    public User getCurrentUser() {
        Client client = prepareClient();
        WebResource webResource = client.resource(GET_CURRENT_USER);

        String response = webResource.get(String.class);
        JSONObject object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);

        return new User(data.toJSONString());
    }

    /**
     * Get current user, including user's data.
     * It is possible to get the user's time entries, projects, tasks, tags, 
     * workspaces and clients when requesting the current user's data. 
     * 
     * @return current user {@link User}
     */
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

    private JSONObject createProjectUserRequestParameter(ProjectUser projectUser) {
        JSONObject object = new JSONObject();
        object.put("project_user", projectUser.toJSONObject());
        return object;
    }

    private JSONObject createTaskRequestParameter(Task task) {
        JSONObject object = new JSONObject();
        object.put("task", task.toJSONObject());
        return object;
    }
}
