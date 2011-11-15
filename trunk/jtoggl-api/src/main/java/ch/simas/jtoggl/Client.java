package ch.simas.jtoggl;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Client {

    private Long id;
    private String name;
    private String hourly_rate;
    private String currency;
    private Workspace workspace;

    public Client() {
    }

    public Client(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.id = (Long) object.get("id");
        this.name = (String) object.get("name");
        this.hourly_rate = (String) object.get("hourly_rate");
        this.currency = (String) object.get("currency");

        JSONObject workspaceObject = (JSONObject) object.get("workspace");
        if (workspaceObject != null) {
            this.workspace = new Workspace(workspaceObject.toJSONString());
        }
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(String hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        if (id != null) {
            object.put("id", id);
        }
        if (name != null) {
            object.put("name", name);
        }
        if (hourly_rate != null) {
            object.put("hourly_rate", hourly_rate);
        }
        if (currency != null) {
            object.put("currency", currency);
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
        return "Client{" + "id=" + id + ", name=" + name + ", hourly_rate=" + hourly_rate + ", currency=" + currency + ", workspace=" + workspace + '}';
    }
}
