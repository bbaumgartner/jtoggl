package ch.simas.jtoggl;

import java.util.Calendar;
import org.junit.Assert;
import org.junit.BeforeClass;
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
        TimeEntries entries = jToggl.getTimeEntries();
        
        Assert.assertFalse(entries.getEntries().isEmpty()); 
    }

    @Test
    public void getTimeEntriesWithRange() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 11, 10);
        TimeEntries entries = jToggl.getTimeEntries(cal.getTime(), cal.getTime());

        Assert.assertTrue(entries.getEntries().isEmpty()); 
    }
}
