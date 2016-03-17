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
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Simon Martinelli
 */
@Test
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
        jToggl.setThrottlePeriod(1100l);
        jToggl.switchLoggingOn();

        getWorkspaces();
    TimeEntry current = jToggl.getCurrentTimeEntry();
    if (current != null && current.getId() != null) {
        jToggl.stopTimeEntry(current);
    }

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
    public void createTimeEntry() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDuration(480);
        entry.setBillable(true);
        entry.setStart(DateTime.parse("2011-09-15T08:00:00"));
        entry.setStop(DateTime.parse("2011-09-15T16:00:00"));
        entry.setDescription("From JUnit Test");
        entry.setCreatedWith("JUnit");

        TimeEntry e = jToggl.createTimeEntry(entry);
        assertNotNull(e);
        assertNotNull(e.getId());

        timeEntry = e;
    }

    @Test(dependsOnMethods = {"getWorkspaces"})
    public void createClient() {
        ProjectClient cl = new ProjectClient();
        cl.setName("JUnit Client");
        cl.setWorkspace(workspace);

        List<ProjectClient> wc = jToggl.getWorkspaceClients(workspace.getId());
        if (wc != null) {
            for (ProjectClient c : wc) {
                if ("JUnit Client".equals(c.getName())) {
                    client = c;
                    return;
                }
            }
        }

        cl = jToggl.createClient(cl);
        assertNotNull(cl);

        client = cl;
    }

    @Test(dependsOnMethods = {"createClient"})
    public void createProject() {
        List<Project> projects = jToggl.getProjects();
        if (projects != null) {
            for (Project project : projects) {
                if ("JUnit Project".equals(project.getName())) {
                    this.project = project;
                    return;
                }
            }
        }

        project = new Project();
        project.setName("JUnit Project");
        project.setClientId(client.getId());

        List<Workspace> ws = jToggl.getWorkspaces();
        project.setWorkspace(ws.get(0));

        project = jToggl.createProject(project);
        assertNotNull(project);
    }

    @Test(dependsOnMethods = {"createProject"})
    public void createTask() {
        Task t = new Task();
        t.setName("JUnit Task " + DateTime.now().toString());
        t.setActive(true);
        t.setProject(project);

        t = jToggl.createTask(t);
        assertNotNull(t);
        task = t;
    }

    @Test(dependsOnMethods = "createTimeEntry")
    public void getTimeEntries() {
        List<TimeEntry> entries = jToggl.getTimeEntries();

        for (TimeEntry te : entries) {
            System.out.println(te);
        }

        assertFalse(entries.isEmpty());
    }

    @Test(dependsOnMethods = "createTimeEntry")
    public void getTimeEntriesWithRange() {
        List<TimeEntry> entries = jToggl.getTimeEntries(LocalDate.parse("2011-10-10"), LocalDate.parse("2011-10-10"));

        assertTrue(entries.isEmpty());
    }

    @Test(dependsOnMethods = "createTimeEntry")
    public void getTimeEntriesWithRange2() {
        List<TimeEntry> entries = jToggl.getTimeEntries(timeEntry.getStart().toLocalDate(), timeEntry.getStop()
                .toLocalDate());

        assertTrue(!entries.isEmpty());
    }

    @Test(dependsOnMethods = "createTimeEntry")
    public void getTimeEntry() {
        TimeEntry te = jToggl.getTimeEntry(timeEntry.getId());

        assertNotNull(te);
    }

    @Test
    public void getMissingTimeEntry() {
        TimeEntry te = jToggl.getTimeEntry(1l);

        assertNull(te);
    }

    @Test(dependsOnMethods = "createTimeEntry")
    public void updateTimeEntry() {
        final String DESCRIPTION = "ABC";

        timeEntry.setDescription(DESCRIPTION);
        TimeEntry te = jToggl.updateTimeEntry(timeEntry);

        assertNotNull(te);
        Assert.assertEquals( te.getDescription(),DESCRIPTION);
    }

    @Test(dependsOnMethods = "createProject")
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
            assertNotNull(te.getId());//created
            Assert.assertTrue(te.getDuration() < 0);//running

            current = jToggl.getCurrentTimeEntry();
            assertNotNull(current);
            Assert.assertEquals(current.getId(), te.getId());

            Thread.sleep(2000);

            TimeEntry stoppedTe = jToggl.stopTimeEntry(te);

            assertEquals(te.getId(), stoppedTe.getId());
            assertTrue(stoppedTe.getDuration() > 1, stoppedTe.toString()); //stopped

            current = jToggl.getCurrentTimeEntry();
            Assert.assertNull(current);
        } finally {
            jToggl.destroyTimeEntry(te.getId());
        }
    }

    @Test
    public static void getWorkspaces() {
        List<Workspace> workspaces = jToggl.getWorkspaces();

        assertFalse(workspaces.isEmpty());
        workspace = workspaces.get(0);

    }

    @Test(dependsOnMethods = "createClient")
    public void getClients() {
        List<ProjectClient> clients = jToggl.getClients();

        assertFalse(clients.isEmpty());
    }

    @Test(dependsOnMethods = "createClient")
    public void updateClient() {

        client.setNotes("Making more notes for update! " + DateTime.now().toString());
        ProjectClient cl = jToggl.updateClient(client);

        assertNotNull(cl);
        Assert.assertEquals(cl.getNotes(), client.getNotes());
    }

    @Test(dependsOnMethods = "createProject")
    public void getProjects() {
        List<Project> projects = jToggl.getProjects();

        assertFalse(projects.isEmpty());
    }

    @Test(dependsOnMethods = "createProject")
    public void updateProject() {
        project.setBillable(true);
        Project pr = jToggl.updateProject(project);

        assertNotNull(pr);
        if (workspace.getPremium()) {
            Assert.assertTrue(pr.isBillable());
        }
    }

    @Test
    public void createProjectUser() {
        // TODO
    }

    @Test(dependsOnMethods = "createTask")
    public void getTasks() {
        boolean isPremium = false;
        List<Workspace> workspaces = jToggl.getWorkspaces();
        if (!workspaces.isEmpty()) {
            isPremium = workspaces.get(0).getPremium();
        }
        List<Task> tasks = jToggl.getTasks();

        // TODO Task is only available in payed version
        assertFalse(isPremium && tasks.isEmpty());
    }

    @Test(dependsOnMethods = "createTask")
    public void updateTask() {
        if (task == null) return;
        task.setActive(false);
        try {
            Task t = jToggl.updateTask(task);
            assertNotNull(t);
            assertFalse(t.isIs_active());
        } catch (Exception e) {
            // Ignore because Task is only for paying customers
        }
    }

    @Test
    public void getCurrentUser() {
        User user = jToggl.getCurrentUser();

        assertNotNull(user);
        assertNotNull(user.getTimezone());
        Assert.assertTrue(!user.getTimezone().isEmpty());
    }

    @Test(dependsOnMethods = "createProjectUser")
    public void getAllUsers() {
        List<User> users = jToggl.getUsers();

        Assert.assertTrue(!users.isEmpty());
    }

}
