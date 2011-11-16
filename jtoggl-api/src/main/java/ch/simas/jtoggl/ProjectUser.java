package ch.simas.jtoggl;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ProjectUser {

    private Long id;
    private Double hourly_rate;
    private Boolean manager;
    private Project project;
    private User user;

    public ProjectUser() {
    }

    public ProjectUser(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.id = (Long) object.get("id");
        this.hourly_rate = (Double) object.get("hourly_rate");
        this.manager = (Boolean) object.get("manager");

        JSONObject projectObject = (JSONObject) object.get("project");
        if (projectObject != null) {
            this.project = new Project(projectObject.toJSONString());
        }
        JSONObject userObject = (JSONObject) object.get("user");
        if (userObject != null) {
            this.user = new User(userObject.toJSONString());
        }
    }

    public Double getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(Double hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getManager() {
        return manager;
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        if (id != null) {
            object.put("id", id);
        }
        if (hourly_rate != null) {
            object.put("hourly_rate", hourly_rate);
        }
        if (manager != null) {
            object.put("manager", manager);
        }
        if (project != null) {
            object.put("project", this.project.toJSONObject());
        }
        if (user != null) {
            object.put("user", this.user.toJSONObject());
        }

        return object;
    }

    public String toJSONString() {
        return this.toJSONObject().toJSONString();
    }

    @Override
    public String toString() {
        return "ProjectUser{" + "id=" + id + ", hourly_rate=" + hourly_rate + ", manager=" + manager + ", project=" + project + ", user=" + user + '}';
    }
}
