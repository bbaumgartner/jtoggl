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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Simon Martinelli
 */
@XmlRootElement(name = "client")
public class ProjectClient extends AbstractDataWrapper<ProjectClient> implements IData<ProjectClient>, Cloneable {

    private Long id;
    private String name;
    private String hourly_rate;
    private String currency;
    private Workspace workspace;
    private String notes;
    private Long wid;

    public ProjectClient() {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(String hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }


    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name=" + name + ", hourly_rate=" + hourly_rate + ", currency=" + currency
                + ", notes=" + notes + ", workspace=" + workspace + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProjectClient other = (ProjectClient) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public ProjectClient getData() {
        return super.getData();
    }

    @Override
    public void setData(ProjectClient data) {
        super.setData(data);
    }

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    @Override
    public ProjectClient clone() {
        ProjectClient cl = new ProjectClient();
        cl.id=id;
        cl.name=name;
        cl.hourly_rate=hourly_rate;
        cl.currency=currency;
        cl.workspace=workspace;
        cl.notes=notes;
        cl.wid=wid;
        return cl;
    }
}
