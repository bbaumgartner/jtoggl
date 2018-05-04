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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.http.params.CoreConnectionPNames;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import ch.simas.jtoggl.util.DateUtil;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


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
        HashMap<String,String> queryParams = new HashMap<>();
        if (startDate != null && endDate != null) {
            queryParams.put("start_date", DateUtil.convertDateToString(startDate));
            queryParams.put("end_date", DateUtil.convertDateToString(endDate));
        }
        String response = fetch(TIME_ENTRIES, queryParams);
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
        String url = TIME_ENTRY_BY_ID.replace(PLACEHOLDER, id.toString());

        String response = fetch(url);

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
		String response = fetch(TIME_ENTRY_CURRENT);

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

        JSONObject object = createTimeEntryRequestParameter(timeEntry);
        String response = post(object, TIME_ENTRIES);

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
        JSONObject object = createTimeEntryRequestParameter(timeEntry);
        String response = post(object, TIME_ENTRY_START);

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
        String url = TIME_ENTRY_STOP.replace(PLACEHOLDER, timeEntry.getId().toString());

        JSONObject object = createTimeEntryRequestParameter(timeEntry);
        String response = getClient().body(object.toJSONString()).put(url).body().asString();

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
        String url = TIME_ENTRY_BY_ID.replace(PLACEHOLDER, timeEntry.getId().toString());

        JSONObject object = createTimeEntryRequestParameter(timeEntry);
        String response = getClient().body(object.toJSONString()).put(url).body().asString();

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
        String url = TIME_ENTRY_BY_ID.replace(PLACEHOLDER, id.toString());

        getClient().delete(url);
    }

	/**
	 * Destroy a project.
	 * 
	 * @param id
	 */
	public void destroyProject(Long id) {
        String url = PROJECT_BY_ID.replace(PLACEHOLDER, id.toString());
        getClient().delete(url);
	}

    
    /**
     * Get workspaces.
     * 
     * @return list of {@link Workspace}
     */
    public List<Workspace> getWorkspaces() {
        String response = fetch(WORKSPACES);
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
        String response = fetch(CLIENTS);
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

        JSONObject object = createClientRequestParameter(clientObject);
        String url = CLIENTS;
        String response = post(object, url);

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
        String url = CLIENT_BY_ID.replace(PLACEHOLDER, clientObject.getId().toString());

        JSONObject object = createClientRequestParameter(clientObject);
        String response = getClient().body(object.toJSONString()).put(url).body().asString();

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
        String url = CLIENT_BY_ID.replace(PLACEHOLDER, id.toString());
        getClient().delete(url);
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
        JSONObject object = createProjectRequestParameter(project);
        String response = post(object, PROJECTS);

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
        String url = PROJECT_BY_ID.replace(PLACEHOLDER, project.getId().toString());
        JSONObject object = createProjectRequestParameter(project);
        String response = getClient().body(object.toJSONString()).put(url).body().asString();

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
        JSONObject object = createProjectUserRequestParameter(projectUser);
        String response = post(object, PROJECT_USERS);


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
        JSONObject object = createTaskRequestParameter(task);
        String response = post(object, TASKS);

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
        String url = TASK_BY_ID.replace(PLACEHOLDER, task.getId().toString());
        JSONObject object = createTaskRequestParameter(task);

        String response = getClient().body(object.toJSONString()).put(url).body().asString();

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
        String url = TASK_BY_ID.replace(PLACEHOLDER, id.toString());
        getClient().delete(url);
    }

    /**
     * Get current user.
     * 
     * @return current user {@link User}
     */
    public User getCurrentUser() {
        String response = fetch(GET_CURRENT_USER);
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
		String url = WORKSPACE_USERS.replace(PLACEHOLDER, String.valueOf(workspaceId));

		String response = fetch(url);
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
		String url = WORKSPACE_PROJECTS.replace(PLACEHOLDER, String.valueOf(workspaceId));

		String response = fetch(url);
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
		String url = WORKSPACE_CLIENTS.replace(PLACEHOLDER, String.valueOf(workspaceId));
		String response = fetch(url);
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
		String url = WORKSPACE_TASKS.replace(PLACEHOLDER, String.valueOf(workspaceId));
		String response = fetch(url);
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

    private String fetch(String url) {
        return fetch(url, new HashMap<String, String>());
    }

    private String post(JSONObject object, String url) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        RequestSpecification client = getClient();
        Response response = client
                .body(object.toJSONString())
                .post(url);
        if (response.getStatusCode() == 403) {
            throw new RuntimeException("forbidden");
        } else if (response.getStatusCode() == 404) {
            throw new RuntimeException("not found: " + url);
        } else if (response.getStatusCode() == 400) {
            throw new RuntimeException("bad request: " + response.getBody().asString());
        }
        return response.body().asString();
    }

    private String fetch(String url, Map<String, String> params) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        RequestSpecification client = getClient();
        Response response = client
                .params(params)
                .get(url);
        if (response.getStatusCode() == 403) {
            throw new RuntimeException("forbidden");
        } else if (response.getStatusCode() == 404) {
            throw new RuntimeException("not found: " + url);
        } else if (response.getStatusCode() == 400) {
            throw new RuntimeException("bad request: " + response.getBody().asString());
        }
        return response.body().asString();
    }

    private RequestSpecification getClient() {
        RestAssuredConfig config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 1000));

        RequestSpecification result = RestAssured.given().config(config);
        if (log) {
            result = result
                    .filter(new RequestLoggingFilter())
                    .filter(new ResponseLoggingFilter());
        }
        return result.auth().preemptive().basic(user, password).contentType(ContentType.JSON);
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
