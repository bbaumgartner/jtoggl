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

import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Simon Martinelli
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends AbstractDataWrapper<User> implements IData<User> {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("jquery_timeofday_format")
    private String jqueryTimeOfDayFormat;
    @JsonProperty("api_token")
    private String apiToken;
    @JsonProperty("retention")
    private Long timeEntryRetentionDays;
    @JsonProperty("jquery_date_format")
    private String jqueryDateFormat;
    @JsonProperty("date_format")
    private String dateFormat;
    @JsonProperty("default_wid")
    private Long defaultWorkspaceId;
    @JsonProperty("fullname")
    private String fullname;
    @JsonProperty("language")
    private String language;
    @JsonProperty("beginning_of_week")
    private Long beginningOfWeek;
    @JsonProperty("timeofday_format")
    private String timeofdayFormat;
    @JsonProperty("email")
    private String email;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("store_start_and_stop_time")
    private Boolean storeStartAndStopTime;

    public User() {
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Long getBeginningOfWeek() {
        return beginningOfWeek;
    }

    public void setBeginningOfWeek(Long beginningOfWeek) {
        this.beginningOfWeek = beginningOfWeek;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Long getDefaultWorkspaceId() {
        return defaultWorkspaceId;
    }

    public void setDefaultWorkspaceId(Long defaultWorkspaceId) {
        this.defaultWorkspaceId = defaultWorkspaceId;
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

    public String getJqueryDateFormat() {
        return jqueryDateFormat;
    }

    public void setJqueryDateFormat(String jqueryDateFormat) {
        this.jqueryDateFormat = jqueryDateFormat;
    }

    public String getJqueryTimeOfDayFormat() {
        return jqueryTimeOfDayFormat;
    }

    public void setJqueryTimeOfDayFormat(String jqueryTimeOfDayFormat) {
        this.jqueryTimeOfDayFormat = jqueryTimeOfDayFormat;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getTimeEntryRetentionDays() {
        return timeEntryRetentionDays;
    }

    public void setTimeEntryRetentionDays(Long timeEntryRetentionDays) {
        this.timeEntryRetentionDays = timeEntryRetentionDays;
    }

    public String getTimeofdayFormat() {
        return timeofdayFormat;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimeofdayFormat(String timeofdayFormat) {
        this.timeofdayFormat = timeofdayFormat;
    }

    public Boolean getStoreStartAndStopTime() {
        return storeStartAndStopTime;
    }

    public void setStoreStartAndStopTime(Boolean storeStartAndStopTime) {
        this.storeStartAndStopTime = storeStartAndStopTime;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", jqueryTimeOfDayFormat=" + jqueryTimeOfDayFormat + ", apiToken=" +
                apiToken + ", timeEntryRetentionDays=" + timeEntryRetentionDays + ", jqueryDateFormat=" +
                jqueryDateFormat + ", dateFormat=" + dateFormat + ", defaultWorkspaceId=" +
                defaultWorkspaceId + ", fullname=" + fullname + ", language=" + language + ", beginningOfWeek=" +
                beginningOfWeek + ", timeofdayFormat=" + timeofdayFormat + ", email=" + email + '}';
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
