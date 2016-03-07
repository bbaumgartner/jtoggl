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

import ch.simas.jtoggl.domain.Project;
import ch.simas.jtoggl.domain.ProjectClient;
import ch.simas.jtoggl.domain.Task;
import ch.simas.jtoggl.domain.TimeEntry;
import ch.simas.jtoggl.domain.User;
import ch.simas.jtoggl.domain.Workspace;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Simon Martinelli
 */
public class JTogglTest {

    private static JToggl jToggl;
    private static TimeEntry timeEntry;
    private static ProjectClient client;
    private static Project project;
    private static Task task;
    private static Workspace workspace;

    @BeforeClass
    public static void beforeClass() throws Exception {

        String togglUsername = System.getenv("TOGGL_USERNAME");
        if (togglUsername == null) {
            togglUsername = System.getProperty("TOGGL_USERNAME");
        }
        String togglPassword = System.getenv("TOGGL_PASSWORD");
        if (togglPassword == null) {
            togglPassword = System.getProperty("TOGGL_PASSWORD");
        }

        String togglApiToken = System.getenv("TOGGL_API_TOKEN");
        if (togglApiToken == null) {
            togglApiToken = System.getProperty("TOGGL_API_TOKEN");
        }
        if (togglApiToken == null) {
            if (togglUsername == null) {
                throw new RuntimeException("TOGGL_USERNAME not set.");
            }
            if (togglPassword == null) {
                throw new RuntimeException("TOGGL_PASSWORD not set.");
            }
            togglApiToken = new JToggl(togglUsername, togglPassword).getCurrentUser().getApiToken();
        }
        if (togglApiToken == null) {
            throw new RuntimeException("TOGGL_API_TOKEN not set.");
        }
        jToggl = new JToggl(togglApiToken, "api_token");

        User cu = jToggl.getCurrentUser();

        jToggl.setThrottlePeriod(500l);
        jToggl.switchLoggingOn();

        List<Workspace> workspaces = jToggl.getWorkspaces();
        assertTrue(workspaces.size() > 0);
        workspace = workspaces.get(0);

        client = createClient();
        timeEntry = createTimeEntry();
        project = createProject();
        if (workspace.getPremium())
            task = createTask();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        if (timeEntry != null)
            jToggl.destroyTimeEntry(timeEntry.getId());
        if (client != null)
            jToggl.destroyClient(client.getId());
        try {
            jToggl.destroyTask(task.getId());
        } catch (Exception e) {
            // Ignore because Task is only for paying customers
        }
        if (project != null)
            jToggl.destroyProject(project.getId());
    }

    @Test
    public void getTimeEntries() {
        List<TimeEntry> entries = jToggl.getTimeEntries();

        for (TimeEntry te : entries) {
            System.out.println(te);
        }

        Assert.assertFalse(entries.isEmpty());
    }

    @Test
    public void getTimeEntriesWithRange() {
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 11, 10, 0, 0);
        List<TimeEntry> entries = jToggl.getTimeEntries(cal, cal);

        Assert.assertTrue(entries.isEmpty());
    }

    @Test
    public void getTimeEntriesWithRange2() {
        List<TimeEntry> entries = jToggl.getTimeEntries(timeEntry.getStart(), timeEntry.getStop());

        Assert.assertTrue(!entries.isEmpty());
    }

    @Test
    public void getTimeEntry() {
        TimeEntry te = jToggl.getTimeEntry(timeEntry.getId());

        Assert.assertNotNull(te);
    }

    @Test
    public void getMissingTimeEntry() {
        TimeEntry te = jToggl.getTimeEntry(1l);

        Assert.assertNull(te);
    }

    @Test
    public void updateTimeEntry() {
        final String DESCRIPTION = "ABC";

        timeEntry.setDescription(DESCRIPTION);
        TimeEntry te = jToggl.updateTimeEntry(timeEntry);

        Assert.assertNotNull(te);
        Assert.assertEquals(DESCRIPTION, te.getDescription());
    }

    @Test
    public void startStopTimeEntry() throws Exception {
        TimeEntry current = jToggl.getCurrentTimeEntry();
        Assert.assertNull(current);

        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setWorkspace(workspace);
        timeEntry.setProject(project);
        timeEntry.setDescription("ABCD");
        timeEntry.setCreatedWith("JToggl Unit Test");

        TimeEntry te = jToggl.startTimeEntry(timeEntry);

        try {
            Assert.assertNotNull(te.getId());//created
            Assert.assertTrue(te.getDuration() < 0);//running

            current = jToggl.getCurrentTimeEntry();
            Assert.assertNotNull(current);
            Assert.assertEquals(current.getId(), te.getId());

            Thread.sleep(2000);

            TimeEntry stoppedTe = jToggl.stopTimeEntry(te);

            Assert.assertEquals(te.getId(), stoppedTe.getId());
            Assert.assertTrue(stoppedTe.toString(), stoppedTe.getDuration() > 1); //stopped

            current = jToggl.getCurrentTimeEntry();
            Assert.assertNull(current);
        } finally {
            jToggl.destroyTimeEntry(te.getId());
        }
    }

    @Test
    public void getWorkspaces() {
        List<Workspace> workspaces = jToggl.getWorkspaces();

        Assert.assertFalse(workspaces.isEmpty());
    }

    @Test
    public void getClients() {
        List<ProjectClient> clients = jToggl.getClients();

        Assert.assertFalse(clients.isEmpty());
    }

    @Test
    public void updateClient() {

        client.setNotes("Making more notes for update! " + new Date());
        ProjectClient cl = jToggl.updateClient(client);

        Assert.assertNotNull(cl);
        Assert.assertEquals(client.getNotes(), cl.getNotes());
    }

    @Test
    public void getProjects() {
        List<Project> projects = jToggl.getProjects();

        Assert.assertFalse(projects.isEmpty());
    }

    @Test
    public void updateProject() {
        project.setBillable(true);
        Project pr = jToggl.updateProject(project);

        Assert.assertNotNull(pr);
        if (workspace.getPremium()) {
            Assert.assertTrue(pr.isBillable());
        }
    }

    @Test
    public void createProjectUser() {
        // TODO
    }

    @Test
    public void getTasks() {
        boolean isPremium = false;
        List<Workspace> workspaces = jToggl.getWorkspaces();
        if (!workspaces.isEmpty()) {
            isPremium = workspaces.get(0).getPremium();
        }
        List<Task> tasks = jToggl.getTasks();

        // TODO Task is only available in payed version
        Assert.assertFalse(isPremium && tasks.isEmpty());
    }

    @Test
    public void updateTask() {
        if (task == null) return;
        task.setActive(false);
        try {
            Task t = jToggl.updateTask(task);
            Assert.assertNotNull(t);
            Assert.assertFalse(t.isIs_active());
        } catch (Exception e) {
            // Ignore because Task is only for paying customers
        }
    }

    @Test
    public void getCurrentUser() {
        User user = jToggl.getCurrentUser();

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getTimezone());
        Assert.assertTrue(!user.getTimezone().isEmpty());
    }

    @Test
    public void getAllUsers() {
        List<User> users = jToggl.getUsers();

        Assert.assertTrue(!users.isEmpty());
    }

    private static TimeEntry createTimeEntry() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDuration(480);
        entry.setBillable(true);
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 10, 15, 8, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        entry.setStart(cal);
        cal.set(2011, 10, 15, 16, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        entry.setStop(cal);
        entry.setDescription("From JUnit Test");
        entry.setCreatedWith("JUnit");

        entry = jToggl.createTimeEntry(entry);
        Assert.assertNotNull(entry);

        return entry;
    }

    private static ProjectClient createClient() {
        ProjectClient cl = new ProjectClient();
        cl.setName("JUnit Client");
        cl.setWorkspace(workspace);

        cl = jToggl.createClient(cl);
        Assert.assertNotNull(cl);

        return cl;
    }

    private static Project createProject() {
        List<Project> projects = jToggl.getProjects();
        for (Project project : projects) {
            if ("JUnit Project".equals(project.getName())) {
                return project;
            }
        }

        Project pr = new Project();
        pr.setName("JUnit Project");
        pr.setClientId(client.getId());

        List<Workspace> ws = jToggl.getWorkspaces();
        pr.setWorkspace(ws.get(0));

        pr = jToggl.createProject(pr);
        Assert.assertNotNull(pr);

        return pr;
    }

    private static Task createTask() {
        Task t = new Task();
        t.setName("JUnit Task " + new Date());
        t.setActive(true);
        t.setProject(project);

        t = jToggl.createTask(t);
        Assert.assertNotNull(t);

        return t;
    }
}
