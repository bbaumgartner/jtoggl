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

import ch.simas.jtoggl.domain.*;
import ch.simas.jtoggl.domain.request.*;
import ch.simas.jtoggl.util.DelayFilter;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.util.*;

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
    private Client client;

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
     * @param user     username or api_token
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
    public List<TimeEntry> getTimeEntries(LocalDate startDate, LocalDate endDate) {

        return prepareRequest(TIME_ENTRIES,
                (startDate != null && endDate != null) ? ImmutableMap.of(
                        "start_date", ISODateTimeFormat.dateTime().print(startDate.toDateTimeAtStartOfDay()),
                        "end_date", ISODateTimeFormat.dateTime().print(endDate.toDateTimeAtStartOfDay().plus(Days.days(1))))
                        : null).get(new GenericType<List<TimeEntry>>() {
        });
    }

    /**
     * Get a time entry.
     *
     * @param id
     * @return TimeEntry or null if no Entry is found.
     */
    public TimeEntry getTimeEntry(Long id) {

        return prepareRequest(TIME_ENTRY_BY_ID.replace(PLACEHOLDER, id.toString()))
                .get(TimeEntry.class).getData();
    }

    /**
     * Get running time entry
     *
     * @return The running time entry or null if none
     */
    public TimeEntry getCurrentTimeEntry() {

        return prepareRequest(TIME_ENTRY_CURRENT)
                .get(TimeEntry.class).getData();
    }

    /**
     * Create a new time entry.
     *
     * @param timeEntry
     * @return created {@link TimeEntry}
     */
    public TimeEntry createTimeEntry(TimeEntry timeEntry) {
        return prepareRequest(TIME_ENTRIES)
                .post(Entity.json(new RequestTimeEntry(timeEntry)), TimeEntry.class).getData();
    }

    /**
     * Create and then start the given time entry.
     *
     * @param timeEntry the time entry to start
     * @return created {@link TimeEntry}
     */
    public TimeEntry startTimeEntry(TimeEntry timeEntry) {

        RequestTimeEntry entity = new RequestTimeEntry(timeEntry);

        Response r = prepareRequest(TIME_ENTRY_START)
                .post(Entity.json(entity));
        return prepareRequest(TIME_ENTRY_START)
                .post(Entity.json(new RequestTimeEntry(timeEntry)), TimeEntry.class).getData();
    }

    /**
     * Stop the given time entry.
     *
     * @param timeEntry to time entry to stop
     * @return the stopped {@link TimeEntry}
     */
    public TimeEntry stopTimeEntry(TimeEntry timeEntry) {

        return prepareRequest(TIME_ENTRY_STOP.replace(PLACEHOLDER, timeEntry.getId().toString()))
                .put(Entity.json(new RequestTimeEntry(timeEntry)), TimeEntry.class).getData();
    }

    /**
     * Update a time entry.
     *
     * @param timeEntry
     * @return created {@link TimeEntry}
     */
    public TimeEntry updateTimeEntry(TimeEntry timeEntry) {

        return prepareRequest(TIME_ENTRY_BY_ID.replace(PLACEHOLDER, timeEntry.getId().toString()))
                .put(Entity.json(new RequestTimeEntry(timeEntry)), TimeEntry.class).getData();
    }

    /**
     * Destroy a time entry.
     *
     * @param id
     */
    public void destroyTimeEntry(Long id) {

        prepareRequest(TIME_ENTRY_BY_ID.replace(PLACEHOLDER, id.toString()))
                .delete();
    }

    /**
     * Destroy a project.
     *
     * @param id
     */
    public void destroyProject(Long id) {
        prepareRequest(PROJECT_BY_ID.replace(PLACEHOLDER, id.toString()))
                .delete();
    }


    /**
     * Get workspaces.
     *
     * @return list of {@link Workspace}
     */
    public List<Workspace> getWorkspaces() {

        Invocation.Builder request = prepareRequest(WORKSPACES);
        Response response = request.get();
        List<Workspace> workspaces = response.readEntity(new GenericType<List<Workspace>>() {
        });
        /*List<Workspace> workspaces = request
                .get(new GenericType<List<Workspace>>() {
                });
                */
        return workspaces;
    }

    /**
     * Get clients.
     *
     * @return list of {@link ProjectClient}
     */
    public List<ProjectClient> getClients() {

        return prepareRequest(CLIENTS)
                .get(new GenericType<List<ProjectClient>>() {
                });
    }

    /**
     * Create a new client.
     *
     * @param clientObject
     * @return created {@link ProjectClient}
     */
    public ProjectClient createClient(final ProjectClient clientObject) {

        return prepareRequest(CLIENTS)
                .post(Entity.json(new RequestClient(clientObject)), ProjectClient.class).getData();
    }

    /**
     * Update a client.
     *
     * @param clientObject
     * @return updated {@link ProjectClient}
     */
    public ProjectClient updateClient(ProjectClient clientObject) {

        return prepareRequest(CLIENT_BY_ID.replace(PLACEHOLDER, clientObject.getId().toString()))
                .put(Entity.json(new RequestClient(clientObject)), ProjectClient.class).getData();
    }

    /**
     * Destroy a client.
     *
     * @param id
     */
    public void destroyClient(Long id) {

        prepareRequest(CLIENT_BY_ID.replace(PLACEHOLDER, id.toString()))
                .delete(String.class);
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
     *
     * @param project
     * @return created {@link Project}
     */
    public Project createProject(Project project) {

        return prepareRequest(PROJECTS).post(Entity.json(new RequestProject(project)), Project.class).getData();
    }

    /**
     * Update a project.
     *
     * @param project
     * @return updated {@link Project}
     */
    public Project updateProject(Project project) {

        return prepareRequest(PROJECT_BY_ID.replace(PLACEHOLDER, project.getId().toString()))
                .put(Entity.json(new RequestProject(project)), Project.class).getData();
    }

    /**
     * Create a new project user.
     *
     * @param projectUser
     * @return created {@link ProjectUser}
     */
    public ProjectUser createProjectUser(ProjectUser projectUser) {

        return prepareRequest(PROJECT_USERS)
                .post(Entity.json(new RequestProjectUser(projectUser)), ProjectUser.class).getData();
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

        return prepareRequest(TASKS)
                .post(Entity.json(new RequestTask(task)), Task.class).getData();
    }

    /**
     * Update a task.
     *
     * @param task
     * @return updated {@link Task}
     */
    public Task updateTask(Task task) {

        return prepareRequest(TASK_BY_ID.replace(PLACEHOLDER, task.getId().toString()))
                .put(Entity.json(new RequestTask(task)), Task.class).getData();
    }

    /**
     * Destroy a task.
     *
     * @param id
     */
    public void destroyTask(Long id) {

        prepareRequest(TASK_BY_ID.replace(PLACEHOLDER, id.toString()))
                .delete();
    }

    /**
     * Get current user.
     *
     * @return current user {@link User}
     */
    public User getCurrentUser() {
        return prepareRequest(GET_CURRENT_USER)
                .get(User.class).getData();
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
     * @param workspaceId id of the workspace
     * @return all users
     */
    public List<User> getWorkspaceUsers(long workspaceId) {

        return prepareRequest(WORKSPACE_USERS.replace(PLACEHOLDER, String.valueOf(workspaceId)))
                .get(new GenericType<List<User>>() {
                });
    }

    /**
     * All projects in the workspace with the given id.
     *
     * @param workspaceId id of the workspace
     * @return all projects
     */
    public List<Project> getWorkspaceProjects(long workspaceId) {

        return prepareRequest(WORKSPACE_PROJECTS.replace(PLACEHOLDER, String.valueOf(workspaceId)))
                .get(new GenericType<List<Project>>() {
                });
    }

    /**
     * All clients in the workspace with the given id.
     *
     * @param workspaceId id of the workspace
     * @return all clients
     */
    public List<ProjectClient> getWorkspaceClients(long workspaceId) {

        return prepareRequest(WORKSPACE_CLIENTS.replace(PLACEHOLDER, String.valueOf(workspaceId)))
                .get(new GenericType<List<ProjectClient>>() {
                });
    }

    /**
     * All active tasks in the workspace with the given id.
     *
     * @param workspaceId id of the workspace
     * @return all tasks
     */
    public List<Task> getActiveWorkspaceTasks(long workspaceId) {

        return prepareRequest(WORKSPACE_TASKS.replace(PLACEHOLDER, String.valueOf(workspaceId)))
                .get(new GenericType<List<Task>>() {
                });
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

    protected Client prepareClient() {
        if (client == null) {
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 30 * 1000);
            clientConfig.property(ClientProperties.READ_TIMEOUT, 30 * 1000);
            // clientConfig.register(JacksonConfigurator.class);
            clientConfig.register(JacksonFeature.class);

            client = JerseyClientBuilder.createClient(clientConfig);
            client.register(HttpAuthenticationFeature.basic(user, password));
            if (log) {
                LoggingFilter loggingFilter = new LoggingFilter();
                client.register(loggingFilter);
            }
            if (throttlePeriod > 0L) {
                client.register(new DelayFilter(throttlePeriod));
            }
        }
        return client;
    }
/*
    @Provider
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public static class JacksonConfigurator  extends JacksonJsonProvider implements ContextResolver<ObjectMapper> {

        private ObjectMapper mapper = new ObjectMapper();

        public JacksonConfigurator() {
      //      SerializationConfig serConfig = mapper.getSerializationConfig();
//            serConfig.with(OUTPUT_DATE_FORMAT);
    //        DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
  //          deserializationConfig.with(INPUT_DATE_FORMAT);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        }

        @Override
        public ObjectMapper getContext(Class<?> arg0) {
            return mapper;
        }

    }*/

    public long getThrottlePeriod() {
        return throttlePeriod;
    }

    public void setThrottlePeriod(long throttlePeriod) {
        this.throttlePeriod = throttlePeriod;
    }

    private Invocation.Builder prepareRequest(String url, Map<String, String> params) {

        WebTarget target = prepareClient().target(url);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                target = target.queryParam(entry.getKey(), entry.getValue());
            }
        }
        return target.request(MediaType.APPLICATION_JSON_TYPE);
    }

    private Invocation.Builder prepareRequest(String url) {
        return prepareRequest(url, null);
    }

    public static class AndroidFriendlyFeature implements Feature {

        @Override
        public boolean configure(FeatureContext context) {
            context.register(new AbstractBinder() {
                @Override
                protected void configure() {
                    addUnbindFilter(new Filter() {
                        @Override
                        public boolean matches(Descriptor d) {
                            String implClass = d.getImplementation();
                            return implClass.startsWith(
                                    "org.glassfish.jersey.message.internal.DataSource")
                                    || implClass.startsWith(
                                    "org.glassfish.jersey.message.internal.RenderedImage");
                        }
                    });
                }
            });
            return true;
        }
    }
}
