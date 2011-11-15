package ch.simas.jtoggl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeEntries {

    private List<TimeEntry> entries = new ArrayList<TimeEntry>();
    private Date related_data_updated_at;

    public List<TimeEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<TimeEntry> entries) {
        this.entries = entries;
    }

    public Date getRelated_data_updated_at() {
        return related_data_updated_at;
    }

    public void setRelated_data_updated_at(Date related_data_updated_at) {
        this.related_data_updated_at = related_data_updated_at;
    }

    @Override
    public String toString() {
        return "TimeEntries{" + "entries=" + entries + ", related_data_updated_at=" + related_data_updated_at + '}';
    }
}
