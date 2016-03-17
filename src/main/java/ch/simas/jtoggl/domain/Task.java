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
 * @author Simon Martinelli
 */
@JsonRootName("task")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task implements Cloneable, WithWorkspace, WithUser, WithProject ,WithId{

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("estimated_seconds")
    private Long estimatedSeconds;
    @JsonProperty("active")
    private Boolean active;
    private Workspace workspace;
    private Project project;
    private User user;
    @JsonProperty("pid")
    private Long projectId;
    @JsonProperty("wid")
    private Long workspaceId;
    private Long userId;

    public Task() {
    }

    public Long getEstimatedSeconds() {
        return estimatedSeconds;
    }

    public void setEstimatedSeconds(Long estimatedSeconds) {
        this.estimatedSeconds = estimatedSeconds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIs_active() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Project getProject() {
        return project;
    }

    @Override
    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Long getUserId() {
        if (userId == null && user != null) {
            return user.getId();
        }
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }


    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", name=" + name + ", estimatedSeconds=" + estimatedSeconds + ", active="
                + active + ", workspace=" + workspace + ", project=" + project + ", user=" + user + '}';
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

    @Override
    public Task clone() {
        Task tk = new Task();
        tk.id = id;
        tk.name = name;
        tk.estimatedSeconds = estimatedSeconds;
        tk.active = active;
        tk.workspace = workspace;
        tk.project = project;
        tk.user = user;
        return tk;
    }

    public Long getProjectId() {
        if (projectId == null && project != null) {
            return project.getId();
        }
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getWorkspaceId() {
        if (workspaceId == null && workspace != null) {
            return workspace.getId();
        }
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }
}
