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
package ch.simas.jtoggl.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Toggl project representation.
 *
 * @author Simon Martinelli
 */
@JsonRootName("project")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project implements Cloneable, WithWorkspace, WithId {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("billable")
    private Boolean billable;
    private Workspace workspace;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("is_private")
    private Boolean isPrivate;
    @JsonProperty("template")
    private Boolean template;
    @JsonProperty("cid")
    private Long clientId;
    @JsonProperty("wid")
    private Long workspaceId;

    public Project() {
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public Workspace getWorkspace() {
        return workspace;
    }

    @Override
    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public Boolean isBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }


    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", name=" + name + ", active=" + active + ", isPrivate=" + isPrivate + ", " +
                "template=" + template + ", billable=" + billable + ", clientId=" + clientId + ", workspace=" + workspace + '}';
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

    @Override
    public Project clone() {
        Project pr = new Project();
        pr.id = id;
        pr.name = name;
        pr.billable = billable;
        pr.workspace = workspace;
        pr.active = active;
        pr.isPrivate = isPrivate;
        pr.template = template;
        pr.clientId = clientId;
        return pr;
    }

    @Override
    public Long getWorkspaceId() {
        return workspaceId;
    }

    @Override
    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }
}
