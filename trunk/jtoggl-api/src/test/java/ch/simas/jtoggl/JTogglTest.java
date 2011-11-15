package ch.simas.jtoggl;

import java.util.Calendar;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class JTogglTest {

    private static JToggl jToggl;

    @BeforeClass
    public static void beforeClass() {
        String togglApiToken = System.getenv("TOGGL_API_TOKEN");
        if (togglApiToken == null) {
            throw new RuntimeException("TOGGL_API_TOKEN not set.");
        }
        jToggl = new JToggl(togglApiToken, "api_token");
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
    public void getTimeEntry() {
        TimeEntry timeEntry = jToggl.getTimeEntry(20449723l);

        Assert.assertNotNull(timeEntry);
    }

    @Test
    public void createTimeEntry() throws Exception {
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
        
        Workspace workspace = new Workspace();
        workspace.setId(184554l);
        entry.setWorkspace(workspace);
        
        Project project = new Project();
        project.setId(1213228l);
        entry.setProject(project);
        
        TimeEntry timeEntry = jToggl.createTimeEntry(entry);

        Assert.assertNotNull(timeEntry);
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
}
