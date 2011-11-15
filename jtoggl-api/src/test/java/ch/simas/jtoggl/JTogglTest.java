package ch.simas.jtoggl;

import java.util.Calendar;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JTogglTest {

    private static JToggl jToggl;
    private TimeEntry timeEntry;
    private Client client;
    private Project project;

    @BeforeClass
    public static void beforeClass() {
        String togglApiToken = System.getenv("TOGGL_API_TOKEN");
        if (togglApiToken == null) {
            throw new RuntimeException("TOGGL_API_TOKEN not set.");
        }
        jToggl = new JToggl(togglApiToken, "api_token");
    }

    @Before
    public void before() throws Exception {
        this.client = this.createClient();
        this.timeEntry = this.createTimeEntry();
        this.project = this.createProject();
    }

    @After
    public void after() throws Exception {
        jToggl.destroyTimeEntry(this.timeEntry.getId());
        jToggl.destroyClient(this.client.getId());
    }

    @Test
    public void getTimeEntries() throws Exception {
        List<TimeEntry> entries = jToggl.getTimeEntries();

        Assert.assertFalse(entries.isEmpty());
    }

    @Test
    public void getTimeEntriesWithRange() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 11, 10);
        List<TimeEntry> entries = jToggl.getTimeEntries(cal.getTime(), cal.getTime());

        Assert.assertTrue(entries.isEmpty());
    }

    @Test
    public void getTimeEntry() throws Exception {
        TimeEntry te = jToggl.getTimeEntry(this.timeEntry.getId());

        Assert.assertNotNull(te);
    }

    @Test
    public void updateTimeEntry() {
        final String DESCRIPTION = "ABC";

        timeEntry.setDescription(DESCRIPTION);
        TimeEntry te = jToggl.updateTimeEntry(this.timeEntry);

        Assert.assertNotNull(te);
        Assert.assertEquals(DESCRIPTION, te.getDescription());
    }

    @Test
    public void getWorkspaces() throws Exception {
        List<Workspace> workspaces = jToggl.getWorkspaces();

        Assert.assertFalse(workspaces.isEmpty());
    }

    @Test
    public void getClients() throws Exception {
        List<Client> clients = jToggl.getClients();

        Assert.assertFalse(clients.isEmpty());
    }

    @Test
    public void updateClient() {
        final String NAME = "ABC";

        this.client.setName(NAME);
        Client cl = jToggl.updateClient(this.client);

        Assert.assertNotNull(cl);
        Assert.assertEquals(NAME, cl.getName());
    }

    @Test
    public void getProjects() {
        List<Project> projects = jToggl.getProjects();

        Assert.assertFalse(projects.isEmpty());
    }

    @Test
    public void updateProject() {
        this.project.setBillable(true);
        Project pr = jToggl.updateProject(this.project);

        Assert.assertNotNull(pr);
        Assert.assertTrue(pr.isBillable());
    }

    private TimeEntry createTimeEntry() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDuration(480);
        entry.setBillable(true);
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 10, 15, 8, 0);
        entry.setStart(cal.getTime());
        cal.set(2011, 10, 15, 16, 0);
        entry.setStop(cal.getTime());
        entry.setDescription("From JUnit Test");
        entry.setCreated_with("JUnit");

        entry = jToggl.createTimeEntry(entry);
        Assert.assertNotNull(entry);

        return entry;
    }

    private Client createClient() {
        Client cl = new Client();
        cl.setName("JUnit Client");

        cl = jToggl.createClient(cl);
        Assert.assertNotNull(cl);

        return cl;
    }

    private Project createProject() {
        List<Project> projects = jToggl.getProjects();
        if (projects.isEmpty()) {
            Project pr = new Project();
            pr.setName("JUnit Project");
            pr.setClient(this.client);

            List<Workspace> ws = jToggl.getWorkspaces();
            pr.setWorkspace(ws.get(0));

            pr = jToggl.createProject(pr);
            Assert.assertNotNull(pr);

            return pr;
        } else {
            return projects.get(0);
        }
    }
}