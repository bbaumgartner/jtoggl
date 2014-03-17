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
public class Workspace {

    private Long id;
    private String name;
    private Boolean premium;

    public Workspace() {
    }

    public Workspace(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.id = (Long) object.get("id");
        this.name = (String) object.get("name");
        this.premium = (Boolean) object.get("premium");
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

    public Boolean getPremium() {
		return premium;
	}
    
    public void setPremium(Boolean premium) {
		this.premium = premium;
	}
    
    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        if (id != null) {
            object.put("id", id);
        }
        if (name != null) {
            object.put("name", name);
        }
        if (premium != null) {
        	object.put("premium", premium);
        }
        return object;
    }

    public String toJSONString() {
        return this.toJSONObject().toJSONString();
    }

    @Override
    public String toString() {
        return "Workspace{" + "id=" + id + ", name=" + name + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Workspace other = (Workspace) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
