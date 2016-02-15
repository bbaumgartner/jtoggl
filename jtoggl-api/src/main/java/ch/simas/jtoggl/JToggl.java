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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import ch.simas.jtoggl.util.DateUtil;
import ch.simas.jtoggl.util.DelayFilter;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * API Class for Toggl REST API.
 * 
 * @author Simon Martinelli
 */
public class JToggl {

	public static final String API_ROOT = "https://www.toggl.com/api";
	public static final int API_VERSION = 8;
	public static final String API_BASE = String.format("%s/v%d/", API_ROOT, API_VERSION);
	
    public static final String DATA = "data";
    public static final String PLACEHOLDER = "{0}";
    public static final String SIMPLE_ID_PATH = "/" + PLACEHOLDER;
    
    private final static String TIME_ENTRIES = API_BASE + "time_entries";
    private final static String TIME_ENTRY_BY_ID = TIME_ENTRIES + SIMPLE_ID_PATH;
	private final static String TIME_ENTRY_CURRENT = TIME_ENTRIES + "/current";
	private final static String TIME_ENTRY_START = TIME_ENTRIES + "/start";
	private final static String TIME_ENTRY_STOP = TIME_ENTRIES + SIMPLE_ID_PATH + "/stop";
	
    private final static String WORKSPACES = API_BASE + "workspaces";
    private final static String WORKSPACE_BY_ID = WORKSPACES + SIMPLE_ID_PATH;
	private final static String WORKSPACE_USERS = WORKSPACE_BY_ID + "/users";
	private final static String WORKSPACE_PROJECTS = WORKSPACE_BY_ID + "/projects";
	private final static String WORKSPACE_TASKS = WORKSPACE_BY_ID + "/tasks";
	private final static String WORKSPACE_CLIENTS = WORKSPACE_BY_ID + "/clients";
	
    private final static String CLIENTS = API_BASE + "clients";
    private final static String CLIENT_BY_ID = CLIENTS + SIMPLE_ID_PATH;
    
    private final static String PROJECTS = API_BASE + "projects";
    private final static String PROJECT_BY_ID = PROJECTS + SIMPLE_ID_PATH;
    
    private final static String TASKS = API_BASE + "tasks";
    private final static String TASK_BY_ID = TASKS + SIMPLE_ID_PATH;
    
    private final static String TAGS = API_BASE + "tags";
    
    private final static String PROJECT_USERS = WORKSPACES + "/673279/project_users";
    private final static String GET_CURRENT_USER = API_BASE + "me";
    private final String user;
    private final String password;
    
    private boolean log = false;
    private long throttlePeriod = 0L;
    
    /**
     * Constructor to create an instance of JToggl that uses an api token to connect to toggl.
     * 
     * @param apiToken the api token to connect to toggl
     */
    public JToggl(String apiToken) {
    	this(apiToken, "api_token");
    }

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
            MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
            queryParams.add("start_date", DateUtil.convertDateToString(startDate));
            queryParams.add("end_date", DateUtil.convertDateToString(endDate));
            webResource = webResource.queryParams(queryParams);
        }
        String response = webResource.get(String.class);
        JSONArray data = (JSONArray) JSONValue.parse(response);

        List<TimeEntry> entries = new ArrayList<TimeEntry>();
        if (data != null) {
	        for (Object obj : data) {
	            JSONObject entryObject = (JSONObject) obj;
	            entries.add(new TimeEntry(entryObject.toJSONString()));
	        }
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
        String url = TIME_ENTRY_BY_ID.replace(PLACEHOLDER, id.toString());
        WebResource webResource = client.resource(url);

        String response = null;
        try {
            response = webResource.get(String.class);
        } catch (UniformInterfaceException uniformInterfaceException) {
            return null;
        }

        JSONObject object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);
        if (data == null)
        	return null;

        return new TimeEntry(data.toJSONString());
    }

	/**
	 * Get running time entry
	 * 
	 * @return The running time entry or null if none
	 */
	public TimeEntry getCurrentTimeEntry() {
		Client client = prepareClient();
		WebResource webResource = client.resource(TIME_ENTRY_CURRENT);

		String response = null;
		try {
			response = webResource.get(String.class);
		} catch (UniformInterfaceException uniformInterfaceException) {
			return null;
		}

		JSONObject object = (JSONObject) JSONValue.parse(response);
		JSONObject data = (JSONObject) object.get(DATA);
		if (data == null)
			return null;

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
	 * Create and then start the given time entry.
	 * 
	 * @param timeEntry
	 *            the time entry to start
	 * @return created {@link TimeEntry}
	 */
    public TimeEntry startTimeEntry(TimeEntry timeEntry) {
        Client client = prepareClient();
        WebResource webResource = client.resource(TIME_ENTRY_START);

        JSONObject object = createTimeEntryRequestParameter(timeEntry);
        String response = webResource.entity(
                object.toJSONString(), MediaType.APPLICATION_JSON_TYPE).post(String.class);

        object = (JSONObject) JSONValue.parse(response);
        JSONObject data = (JSONObject) object.get(DATA);
        return new TimeEntry(data.toJSONString());
    }
    
	/**
	 * Stop the given time entry.
	 * 
	 * @param timeEntry
	 *            to time entry to stop
	 * @return the stopped {@link TimeEntry}
	 */
    public TimeEntry stopTimeEntry(TimeEntry timeEntry) {
    	Client client = prepareClient();
        String url = TIME_ENTRY_STOP.replace(PLACEHOLDER, timeEntry.getId().toString());
        WebResource webResource = client.resource(url);

        JSONObject object = createTimeEntryRequestParameter(timeEntry);
        String response = webResource.entity(
                object.toJSONString(), MediaType.APPLICATION_JSON_TYPE).put(String.class);

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
        String url = TIME_ENTRY_BY_ID.replace(PLACEHOLDER, timeEntry.getId().toString());
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
        String url = TIME_ENTRY_BY_ID.replace(PLACEHOLDER, id.toString());
        WebResource webResource = client.resource(url);

        webResource.delete(String.class);
    }

	/**
	 * Destroy a project.
	 * 
	 * @param id
	 */
	public void destroyProject(Long id) {
		Client client = prepareClient();
        String url = PROJECT_BY_ID.replace(PLACEHOLDER, id.toString());
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
        JSONArray data = (JSONArray) JSONValue.parse(response);

        List<Workspace> workspaces = new ArrayList<Workspace>();
        if (data != null) {
	        for (Object obj : data) {
	            JSONObject entryObject = (JSONObject) obj;
	            workspaces.add(new Workspace(entryObject.toJSONString()));
	        }
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
        JSONArray data = (JSONArray) JSONValue.parse(response);

        List<ch.simas.jtoggl.Client> clients = new ArrayList<ch.simas.jtoggl.Client>();
        if (data != null) {
	        for (Object obj : data) {
	            JSONObject entryObject = (JSONObject) obj;
	            clients.add(new ch.simas.jtoggl.Client(entryObject.toJSONString()));
	        }
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
        String url = CLIENT_BY_ID.replace(PLACEHOLDER, clientObject.getId().toString());
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
        String url = CLIENT_BY_ID.replace(PLACEHOLDER, id.toString());
        WebResource webResource = client.resource(url);

        webResource.delete(String.class);
    }

    /**
     * Get projects.
     * 
     * @return list of {@link Project}
     */
    public List<Project> getProjects() {
        List<Project> projects = new ArrayList<Project>();
        
        List<Workspace> workspaces = getWorkspaces();
        for (Workspace workspace : workspaces) {
			List<Project> workspaceProjects = getWorkspaceProjects(workspace.getId());
			for (Project project : workspaceProjects) {
				project.setWorkspace(workspace);
			}
			projects.addAll(workspaceProjects);
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
        String url = PROJECT_BY_ID.replace(PLACEHOLDER, project.getId().toString());
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
        List<Task> tasks = new ArrayList<Task>();
        
        List<Workspace> workspaces = getWorkspaces();
        for (Workspace workspace : workspaces) {
			List<Task> workspaceTasks = getActiveWorkspaceTasks(workspace.getId());
			tasks.addAll(workspaceTasks);
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
        String url = TASK_BY_ID.replace(PLACEHOLDER, task.getId().toString());
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
        String url = TASK_BY_ID.replace(PLACEHOLDER, id.toString());
        WebResource webResource = client.resource(url);

        webResource.delete(String.class);
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

	/**
	 * All users in the workspace with the given id.
	 * 
	 * @param workspaceId
	 *            id of the workspace
	 * @return all users
	 */
	public List<User> getWorkspaceUsers(long workspaceId) {
		Client client = prepareClient();
		String url = WORKSPACE_USERS.replace(PLACEHOLDER, String.valueOf(workspaceId));
		WebResource webResource = client.resource(url);

		String response = webResource.get(String.class);
		JSONArray data = (JSONArray) JSONValue.parse(response);

		List<User> users = new ArrayList<User>();
		if (data != null) {
			for (Object obj : data) {
				JSONObject entryObject = (JSONObject) obj;
				users.add(new User(entryObject.toJSONString()));
			}
		}
		return users;
	}
	
	/**
	 * All projects in the workspace with the given id.
	 * 
	 * @param workspaceId
	 *            id of the workspace
	 * @return all projects
	 */
	public List<Project> getWorkspaceProjects(long workspaceId) {
		Client client = prepareClient();
		String url = WORKSPACE_PROJECTS.replace(PLACEHOLDER, String.valueOf(workspaceId));
		WebResource webResource = client.resource(url);

		String response = webResource.get(String.class);
		JSONArray data = (JSONArray) JSONValue.parse(response);

		List<Project> projects = new ArrayList<Project>();
		if (data != null) {
			for (Object obj : data) {
				JSONObject entryObject = (JSONObject) obj;
				projects.add(new Project(entryObject.toJSONString()));
			}
		}
		return projects;
	}
	
	/**
	 * All clients in the workspace with the given id.
	 * 
	 * @param workspaceId
	 *            id of the workspace
	 * @return all clients
	 */
	public List<ch.simas.jtoggl.Client> getWorkspaceClients(long workspaceId) {
		Client client = prepareClient();
		String url = WORKSPACE_CLIENTS.replace(PLACEHOLDER, String.valueOf(workspaceId));
		WebResource webResource = client.resource(url);

		String response = webResource.get(String.class);
		JSONArray data = (JSONArray) JSONValue.parse(response);

        List<ch.simas.jtoggl.Client> clients = new ArrayList<ch.simas.jtoggl.Client>();
        if (data != null) {
	        for (Object obj : data) {
	            JSONObject entryObject = (JSONObject) obj;
	            clients.add(new ch.simas.jtoggl.Client(entryObject.toJSONString()));
	        }
        }
        return clients;
	}
	
	/**
	 * All active tasks in the workspace with the given id.
	 * 
	 * @param workspaceId
	 *            id of the workspace
	 * @return all tasks
	 */
	public List<Task> getActiveWorkspaceTasks(long workspaceId) {
		Client client = prepareClient();
		String url = WORKSPACE_TASKS.replace(PLACEHOLDER, String.valueOf(workspaceId));
		WebResource webResource = client.resource(url);

		String response = webResource.get(String.class);
		JSONArray data = (JSONArray) JSONValue.parse(response);

		List<Task> tasks = new ArrayList<Task>();
		if (data != null) {
			for (Object obj : data) {
				JSONObject entryObject = (JSONObject) obj;
				tasks.add(new Task(entryObject.toJSONString()));
			}
		}
		return tasks;
	}

	/**
	 * All users in all workspaces.
	 * 
	 * @return all users in all workspaces
	 */
	public List<User> getUsers() {
		HashSet<User> result = new HashSet<User>();
		List<Workspace> workspaces = getWorkspaces();
		for (Workspace workspace : workspaces) {
			List<User> workspaceUsers = getWorkspaceUsers(workspace.getId());
			result.addAll(workspaceUsers);
		}
		return new ArrayList<User>(result);
	}
	
    /**
     * Switch logging on.
     */
    public void switchLoggingOn() {
        this.log = true;
    }

    /**
     * Switch logging off.
     */
    public void switchLoggingOff() {
        this.log = false;
    }

    private Client prepareClient() {
		DefaultClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 30 * 1000);
		clientConfig.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, 30 * 1000);
		Client client = Client.create(clientConfig);
        client.addFilter(new HTTPBasicAuthFilter(user, password));
        if (log) {
            LoggingFilter loggingFilter = new LoggingFilter();
			client.addFilter(loggingFilter);
        }
        if (throttlePeriod > 0L)
        {
        	client.addFilter(new DelayFilter(throttlePeriod));
        }
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

	public long getThrottlePeriod() {
		return throttlePeriod;
	}

	public void setThrottlePeriod(long throttlePeriod) {
		this.throttlePeriod = throttlePeriod;
	}

}
