package ch.simas.jtoggl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeEntry {

    private Long id;
    private String description;
    private Project project;
    private Date start;
    private Date stop;
    private long duration;
    private boolean billable;
    private Workspace workspace;
    private List<String> tag_names = new ArrayList<String>();
    private boolean ignore_start_and_stop;

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isIgnore_start_and_stop() {
        return ignore_start_and_stop;
    }

    public void setIgnore_start_and_stop(boolean ignore_start_and_stop) {
        this.ignore_start_and_stop = ignore_start_and_stop;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public List<String> getTag_names() {
        return tag_names;
    }

    public void setTag_names(List<String> tag_names) {
        this.tag_names = tag_names;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public String toString() {
        return "TimeEntry{" + "id=" + id + ", description=" + description + ", project=" + project + ", start=" + start + ", stop=" + stop + ", duration=" + duration + ", billable=" + billable + ", workspace=" + workspace + ", tag_names=" + tag_names + ", ignore_start_and_stop=" + ignore_start_and_stop + '}';
    }
}
