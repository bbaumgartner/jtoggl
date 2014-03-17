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
        this.hourly_rate = (Double) object.get("rate");
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
            object.put("rate", hourly_rate);
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProjectUser other = (ProjectUser) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
