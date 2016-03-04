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


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Simon Martinelli
 */
@XmlRootElement
public class User extends AbstractDataWrapper<User> implements IData<User> {

    private Long id;
    private String jquery_timeofday_format;
    private String api_token;
    private Long time_entry_retention_days;
    private String jquery_date_format;
    private String date_format;
    @XmlElement(name="default_wid")
    private Long default_workspace_id;
    private String fullname;
    private String language;
    private Long beginning_of_week;
    private String timeofday_format;
    private String email;
    @XmlElement(name="timezone")
    private String timezone;
    private Boolean storeStartAndStopTime;

    public User() {
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public Long getBeginning_of_week() {
        return beginning_of_week;
    }

    public void setBeginning_of_week(Long beginning_of_week) {
        this.beginning_of_week = beginning_of_week;
    }

    public String getDate_format() {
        return date_format;
    }

    public void setDate_format(String date_format) {
        this.date_format = date_format;
    }

    public Long getDefault_workspace_id() {
        return default_workspace_id;
    }

    public void setDefault_workspace_id(Long default_workspace_id) {
        this.default_workspace_id = default_workspace_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJquery_date_format() {
        return jquery_date_format;
    }

    public void setJquery_date_format(String jquery_date_format) {
        this.jquery_date_format = jquery_date_format;
    }

    public String getJquery_timeofday_format() {
        return jquery_timeofday_format;
    }

    public void setJquery_timeofday_format(String jquery_timeofday_format) {
        this.jquery_timeofday_format = jquery_timeofday_format;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getTime_entry_retention_days() {
        return time_entry_retention_days;
    }

    public void setTime_entry_retention_days(Long time_entry_retention_days) {
        this.time_entry_retention_days = time_entry_retention_days;
    }

    public String getTimeofday_format() {
        return timeofday_format;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimeofday_format(String timeofday_format) {
        this.timeofday_format = timeofday_format;
    }

    public Boolean getStoreStartAndStopTime() {
        return storeStartAndStopTime;
    }

    public void setStoreStartAndStopTime(Boolean storeStartAndStopTime) {
        this.storeStartAndStopTime = storeStartAndStopTime;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", jquery_timeofday_format=" + jquery_timeofday_format + ", api_token=" +
                api_token + ", time_entry_retention_days=" + time_entry_retention_days + ", jquery_date_format=" +
                jquery_date_format + ", date_format=" + date_format + ", default_workspace_id=" +
                default_workspace_id + ", fullname=" + fullname + ", language=" + language + ", beginning_of_week=" +
                beginning_of_week + ", timeofday_format=" + timeofday_format + ", email=" + email + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public User getData() {
        return super.getData();
    }

    @Override
    public void setData(User data) {
        super.setData(data);
    }
}
