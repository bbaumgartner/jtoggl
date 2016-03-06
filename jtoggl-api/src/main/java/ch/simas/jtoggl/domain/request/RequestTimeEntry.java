package ch.simas.jtoggl.domain.request;

import ch.simas.jtoggl.domain.TimeEntry;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by vranikp on 4.3.16.
 *
 * @author vranikp
 */
@XmlRootElement(name = "client")
public class RequestTimeEntry {
    private TimeEntry time_entry;

    public RequestTimeEntry() {
    }

    public RequestTimeEntry(TimeEntry timeEntry) {
        this.time_entry = timeEntry.clone();
        if (this.time_entry.getWorkspaceId() == null && this.time_entry.getWorkspace() != null) {
            this.time_entry.setWorkspaceId(this.time_entry.getWorkspace().getId());
        }
        if (this.time_entry.getProjectId() == null && this.time_entry.getProject() != null) {
            this.time_entry.setProjectId(this.time_entry.getProject().getId());
        }
        this.time_entry.setProject(null);
        this.time_entry.setWorkspace(null);
        this.time_entry.setData(null);
    }

    public TimeEntry getTime_entry() {
        return time_entry;
    }

    public void setTime_entry(TimeEntry client) {
        this.time_entry = client;
    }

}
