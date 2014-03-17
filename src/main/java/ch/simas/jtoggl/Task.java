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
public class Task {

    private Long id;
    private String name;
    private Long estimated_seconds;
    private Boolean is_active;
    private Workspace workspace;
    private Project project;
    private User user;

    public Task() {
    }

    public Task(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.id = (Long) object.get("id");
        this.name = (String) object.get("name");
        this.estimated_seconds = (Long) object.get("estimated_seconds");
        this.is_active = (Boolean) object.get("active");

        JSONObject workspaceObject = (JSONObject) object.get("workspace");
        if (workspaceObject != null) {
            this.workspace = new Workspace(workspaceObject.toJSONString());
        }
        JSONObject projectObject = (JSONObject) object.get("project");
        if (projectObject != null) {
            this.project = new Project(projectObject.toJSONString());
        }
        JSONObject userObject = (JSONObject) object.get("user");
        if (userObject != null) {
            this.user = new User(userObject.toJSONString());
        }
    }

    public Long getEstimated_seconds() {
        return estimated_seconds;
    }

    public void setEstimated_seconds(Long estimated_seconds) {
        this.estimated_seconds = estimated_seconds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (estimated_seconds != null) {
            object.put("estimated_seconds", estimated_seconds);
        }
        if (is_active != null) {
            object.put("active", is_active);
        }

        if (workspace != null) {
            object.put("workspace", this.workspace.toJSONObject());
            object.put("wid", this.workspace.getId());
        }
        if (project != null) {
            object.put("project", this.project.toJSONObject());
            object.put("pid", this.project.getId());
        }
        if (user != null) {
            object.put("user", this.user.toJSONObject());
            object.put("uid", this.user.getId());
        }

        return object;
    }

    public String toJSONString() {
        return this.toJSONObject().toJSONString();
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", name=" + name + ", estimated_seconds=" + estimated_seconds + ", is_active=" + is_active + ", workspace=" + workspace + ", project=" + project + ", user=" + user + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Task other = (Task) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
