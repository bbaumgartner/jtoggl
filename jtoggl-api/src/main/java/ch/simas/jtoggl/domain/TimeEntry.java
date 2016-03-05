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

    private Long id;
    private String description;
    private Project project;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Calendar start;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Calendar stop;
    private long duration;
    private Boolean billable;
    private Workspace workspace;
    private List<String> tag_names;//= new ArrayList<String>();
    private String created_with;
    private Boolean duronly;
    private Long pid;
    private Long wid;
    private Long tid;

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

    public Boolean getDuronly() {
        return duronly;
    }

    public void setDuronly(Boolean duronly) {
        this.duronly = duronly;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        if (project != null) {
            this.pid = project.getId();
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

    public List<String> getTag_names() {
        return tag_names;
    }

    public void setTag_names(List<String> tag_names) {
        this.tag_names = tag_names;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
        if (workspace != null) {
            this.wid = workspace.getId();
        }
    }

    public String getCreated_with() {
        return created_with;
    }

    public void setCreated_with(String created_with) {
        this.created_with = created_with;
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

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }


    @Override
    public String toString() {
        DateFormat f = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.LONG, SimpleDateFormat.LONG);
        return "TimeEntry{" + "id=" + id + ", description=" + description + ", project=" + project + ", start=" +
                (start == null ? "null" : f.format(start.getTime())) + ", stop=" + (stop == null ? "null" : f.format(stop.getTime())) + ", duration=" + duration + ", billable=" + billable + ", workspace=" +
                workspace + ", tag_names=" + tag_names + ", duronly=" + duronly + ", tid = " + tid + '}';
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
        te.tag_names = tag_names;
        te.created_with = created_with;
        te.duronly = duronly;
        te.pid = pid;
        te.wid = wid;
        te.tid = tid;
        return te;
    }
}
