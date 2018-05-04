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
    private Long wid;
    private Long pid;

    public Task() {
    }

    public Task(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.id = (Long) object.get("id");
        this.name = (String) object.get("name");
        this.estimated_seconds = (Long) object.get("estimated_seconds");
        this.is_active = (Boolean) object.get("active");
        this.wid = (Long) object.get("wid");
        this.pid = (Long) object.get("pid");
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

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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
        if (wid != null) {
            object.put("wid", wid);
        }
        if (pid != null) {
            object.put("pid", pid);
        }

        return object;
    }

    public String toJSONString() {
        return this.toJSONObject().toJSONString();
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", name=" + name + ", estimated_seconds=" + estimated_seconds + ", is_active=" + is_active + ", wid=" + wid + ", pid=" + pid + '}';
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
