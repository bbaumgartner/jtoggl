package ch.simas.jtoggl;

import ch.simas.jtoggl.util.DateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
    private String created_with;

    public TimeEntry() {
    }

    public TimeEntry(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.id = (Long) object.get("id");
        this.description = (String) object.get("description");
        this.start = DateUtil.convertStringToDate((String) object.get("start"));
        this.stop = DateUtil.convertStringToDate((String) object.get("stop"));
        this.duration = (Long) object.get("duration");
        this.billable = (Boolean) object.get("billable");
        this.ignore_start_and_stop = (Boolean) object.get("ignore_start_and_stop");
        created_with = (String) object.get("created_with");


        JSONObject workspaceObject = (JSONObject) object.get("workspace");
        if (workspaceObject != null) {
            this.workspace = new Workspace(workspaceObject.toJSONString());
        }

        JSONObject projectObject = (JSONObject) object.get("project");
        if (projectObject != null) {
            this.project = new Project(projectObject.toJSONString());
        }
        // Tag names
        JSONArray tagsArray = (JSONArray) object.get("tag_names");
        List<String> tags = new ArrayList<String>();
        for (Object arrayObject : tagsArray) {
            tags.add((String) arrayObject);
        }
        this.tag_names = tags;
    }

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

    public String getCreated_with() {
        return created_with;
    }

    public void setCreated_with(String created_with) {
        this.created_with = created_with;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        object.put("billable", billable);
        if (description != null) {
            object.put("description", description);
        }
        if (duration != 0) {
            object.put("duration", duration);
        }
        if (id != null) {
            object.put("id", id);
        }
        object.put("ignore_start_and_stop", ignore_start_and_stop);
        if (start != null) {
            object.put("start", DateUtil.convertDateToString(start));
        }
        if (stop != null) {
            object.put("stop", DateUtil.convertDateToString(stop));
        }
        if (created_with != null) {
            object.put("created_with", created_with);
        }

        if (!this.tag_names.isEmpty()) {
            JSONArray tag_names_arr = new JSONArray();
            tag_names_arr.addAll(this.tag_names);
            object.put("tag_names", tag_names_arr);
        }

        if (project != null) {
            object.put("project", this.project.toJSONObject());
        }

        if (workspace != null) {
            object.put("workspace", this.workspace.toJSONObject());
        }

        return object;
    }

    public String toJSONString() {
        return this.toJSONObject().toJSONString();
    }

    @Override
    public String toString() {
        return "TimeEntry{" + "id=" + id + ", description=" + description + ", project=" + project + ", start=" + start + ", stop=" + stop + ", duration=" + duration + ", billable=" + billable + ", workspace=" + workspace + ", tag_names=" + tag_names + ", ignore_start_and_stop=" + ignore_start_and_stop + '}';
    }
}
