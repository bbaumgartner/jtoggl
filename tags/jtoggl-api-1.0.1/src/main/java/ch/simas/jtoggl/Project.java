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
    private Boolean billable;
    private Workspace workspace;
    private Boolean active;
    private Boolean is_private;
    private Boolean template;
    private Long cid;

    public Project() {
    }

    public Project(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.id = (Long) object.get("id");
        this.name = (String) object.get("name");
        this.billable = (Boolean) object.get("billable");
        this.active = (Boolean) object.get("active");
        this.is_private = (Boolean) object.get("is_private");
        this.template = (Boolean) object.get("template");
        this.cid= (Long) object.get("cid");

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

    public Long getCid() {
		return cid;
	}

    public void setCid(Long cid) {
		this.cid = cid;
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


    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        if (id != null) {
            object.put("id", id);
        }
        if (name != null) {
            object.put("name", name);
        }
        if (billable != null) {
            object.put("billable", billable);
        }
        if (active != null) {
        	object.put("active", active);
        }
        if (is_private != null) {
        	object.put("is_private", is_private);
        }
        if (template != null) {
        	object.put("template", template);
        }
        if (cid != null) {
            object.put("client", this.cid);
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
        return "Project{" + "id=" + id + ", name=" + name + ", active=" + active + ", is_private=" + is_private + ", template=" + template + ", billable=" + billable + ", cid=" + cid + ", workspace=" + workspace + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Project other = (Project) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
