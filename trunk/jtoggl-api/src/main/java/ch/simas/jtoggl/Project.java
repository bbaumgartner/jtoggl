/*
 * jtoggl - Java Wrapper for Toggl REST API https://www.toggl.com/public/api
 *
 * Copyright (C) 2011 by simas GmbH, Moosentli 7, 3235 Erlach, Switzerland
 * http://www.simas.ch
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.simas.jtoggl;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * 
 * @author Simon Martinelli
 */
public class Project {

    private Long id;
    private String name;
    private String client_project_name;
    private Boolean billable;
    private Long estimated_workhours;
    private Boolean automatically_calculate_estimated_workhours;
    private Client client;
    private Workspace workspace;

    public Project() {
    }

    public Project(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.id = (Long) object.get("id");
        this.name = (String) object.get("name");
        this.client_project_name = (String) object.get("client_project_name");
        this.estimated_workhours = (Long) object.get("estimated_workhours");
        this.billable = (Boolean) object.get("billable");
        this.automatically_calculate_estimated_workhours = (Boolean) object.get("automatically_calculate_estimated_workhours");

        JSONObject clientObject = (JSONObject) object.get("client");
        if (clientObject != null) {
            this.client = new Client(clientObject.toJSONString());
        }

        JSONObject workspaceObject = (JSONObject) object.get("workspace");
        if (workspaceObject != null) {
            this.workspace = new Workspace(workspaceObject.toJSONString());
        }

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public Boolean isBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public Boolean getAutomatically_calculate_estimated_workhours() {
        return automatically_calculate_estimated_workhours;
    }

    public void setAutomatically_calculate_estimated_workhours(Boolean automatically_calculate_estimated_workhours) {
        this.automatically_calculate_estimated_workhours = automatically_calculate_estimated_workhours;
    }

    public Long getEstimated_workhours() {
        return estimated_workhours;
    }

    public void setEstimated_workhours(Long estimated_workhours) {
        this.estimated_workhours = estimated_workhours;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        if (id != null) {
            object.put("id", id);
        }
        if (name != null) {
            object.put("name", name);
        }
        if (client_project_name != null) {
            object.put("client_project_name", client_project_name);
        }
        if (billable != null) {
            object.put("billable", billable);
        }
        if (estimated_workhours != null) {
            object.put("estimated_workhours", estimated_workhours);
        }
        if (automatically_calculate_estimated_workhours != null) {
            object.put("automatically_calculate_estimated_workhours", automatically_calculate_estimated_workhours);
        }
        if (client != null) {
            object.put("client", this.client.toJSONObject());
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
        return "Project{" + "id=" + id + ", name=" + name + ", client_project_name=" + client_project_name + ", billable=" + billable + ", estimated_workhours=" + estimated_workhours + ", automatically_calculate_estimated_workhours=" + automatically_calculate_estimated_workhours + ", client=" + client + ", workspace=" + workspace + '}';
    }
}
