package ch.simas.jtoggl;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Workspace {

    private Long id;
    private String name;

    public Workspace(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.id = (Long) object.get("id");
        this.name = (String) object.get("name");
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
        return object;
    }
    
    public String toJSONString() {
        return this.toJSONObject().toJSONString();
    }

    @Override
    public String toString() {
        return "Workspace{" + "id=" + id + ", name=" + name + '}';
    }
}
