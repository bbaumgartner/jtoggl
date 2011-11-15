package ch.simas.jtoggl;

import org.junit.Test;

public class JTogglTest {

    @Test
    public void getTimeEntries() throws Exception {
        JToggl jToggl = new JToggl("9c4ce1c2e22c7d179f73c7d714bb99bd", "api_token");

        TimeEntries entries = jToggl.getTimeEntries();

        System.out.println(entries);
    }
}
