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

import ch.simas.jtoggl.CustomDateDeserializer;
import ch.simas.jtoggl.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

import java.util.List;


/**
 * @author Simon Martinelli
 */
@JsonRootName("time_entry")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeEntry implements Cloneable, WithWorkspace, WithId, WithProject {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("description")
    private String description;
    private Project project;
    @JsonProperty("start")
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private DateTime start;
    @JsonProperty("stop")
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private DateTime stop;
    @JsonProperty("duration")
    private long duration;
    @JsonProperty("billable")
    private Boolean billable;
    private Workspace workspace;
    @JsonProperty("tags")
    private List<String> tags;//= new ArrayList<String>();
    @JsonProperty("created_with")
    private String createdWith;
    @JsonProperty("duronly")
    private Boolean durationOnly;
    @JsonProperty("pid")
    private Long projectId;
    @JsonProperty("wid")
    private Long workspaceId;
    @JsonProperty("tid")
    private Long taskId;

    public TimeEntry() {
    }

    public Boolean isBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDurationOnly() {
        return durationOnly;
    }

    public void setDurationOnly(Boolean durationOnly) {
        this.durationOnly = durationOnly;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getStop() {
        return stop;
    }

    public void setStop(DateTime stop) {
        this.stop = stop;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public Workspace getWorkspace() {
        return workspace;
    }

    @Override
    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public String getCreatedWith() {
        return createdWith;
    }

    public void setCreatedWith(String createdWith) {
        this.createdWith = createdWith;
    }

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }

    @Override
    public Long getProjectId() {
        return projectId;
    }

    @Override
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }


    @Override
    public String toString() {
        return "TimeEntry{" + "id=" + id + ", description=" + description + ", project=" + project + ", start=" +
                (start == null ? "null" : start + ", stop=" + (stop == null ? "null" : stop)) + ", duration=" + duration + ", billable=" + billable + ", workspace=" +
                workspace + ", tags=" + tags + ", durationOnly=" + durationOnly + ", taskId = " + taskId + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimeEntry other = (TimeEntry) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public TimeEntry clone() {
        TimeEntry te = new TimeEntry();
        te.id = id;
        te.description = description;
        te.project = project;
        te.start = start;
        te.stop = stop;
        te.duration = duration;
        te.billable = billable;
        te.workspace = workspace;
        te.tags = tags;
        te.createdWith = createdWith;
        te.durationOnly = durationOnly;
        te.projectId = projectId;
        te.workspaceId = workspaceId;
        te.taskId = taskId;
        return te;
    }
}
