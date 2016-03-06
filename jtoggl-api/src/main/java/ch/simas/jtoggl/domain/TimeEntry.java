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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlRootElement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * @author Simon Martinelli
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeEntry extends AbstractDataWrapper<TimeEntry> implements IData<TimeEntry>, Cloneable {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("description")
    private String description;
    private Project project;
    @JsonProperty("start")
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Calendar start;
    @JsonProperty("stop")
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Calendar stop;
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

    public Long getId() {
        return id;
    }

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
        if (project != null) {
            this.projectId = project.getId();
        }
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getStop() {
        return stop;
    }

    public void setStop(Calendar stop) {
        this.stop = stop;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
        if (workspace != null) {
            this.workspaceId = workspace.getId();
        }
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

    public Long getProjectId() {
        return projectId;
    }

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
        DateFormat f = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.LONG, SimpleDateFormat.LONG);
        return "TimeEntry{" + "id=" + id + ", description=" + description + ", project=" + project + ", start=" +
                (start == null ? "null" : f.format(start.getTime())) + ", stop=" + (stop == null ? "null" : f.format(stop.getTime())) + ", duration=" + duration + ", billable=" + billable + ", workspace=" +
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
    public TimeEntry getData() {
        return super.getData();
    }

    @Override
    public void setData(TimeEntry data) {
        super.setData(data);
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
