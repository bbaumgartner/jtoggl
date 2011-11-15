package ch.simas.jtoggl;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Project {

    private Long id;
    private String name;
    private String client_project_name;

    public Project(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.id = (Long) object.get("id");
        this.name = (String) object.get("name");
        this.client_project_name = (String) object.get("client_project_name");
    }

    public String getClient_project_name() {
        return client_project_name;
    }

    public void setClient_project_name(String client_project_name) {
        this.client_project_name = client_project_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("name", name);
        object.put("client_project_name", client_project_name);
        return object;
    }
    
    public String toJSONString() {
        return this.toJSONObject().toJSONString();
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", name=" + name + ", client_project_name=" + client_project_name + '}';
    }
}
