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

public class User {

    private Long id;
    private String jquery_timeofday_format;
    private String api_token;
    private Long time_entry_retention_days;
    private String jquery_date_format;
    private String date_format;
    private Long default_workspace_id;
    private Boolean new_time_entries_start_automatically;
    private String fullname;
    private String language;
    private Long beginning_of_week;
    private String timeofday_format;
    private String email;

    public User() {
    }

    public User(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.id = (Long) object.get("id");
        this.jquery_timeofday_format = (String) object.get("jquery_timeofday_format");
        this.api_token = (String) object.get("api_token");
        this.time_entry_retention_days = (Long) object.get("time_entry_retention_days");
        this.jquery_date_format = (String) object.get("jquery_date_format");
        this.date_format = (String) object.get("date_format");
        this.default_workspace_id = (Long) object.get("default_workspace_id");
        this.new_time_entries_start_automatically = (Boolean) object.get("new_time_entries_start_automatically");
        this.fullname = (String) object.get("fullname");
        this.language = (String) object.get("language");
        this.beginning_of_week = (Long) object.get("beginning_of_week");
        this.timeofday_format = (String) object.get("timeofday_format");
        this.email = (String) object.get("email");
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

    public Boolean getNew_time_entries_start_automatically() {
        return new_time_entries_start_automatically;
    }

    public void setNew_time_entries_start_automatically(Boolean new_time_entries_start_automatically) {
        this.new_time_entries_start_automatically = new_time_entries_start_automatically;
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

    public void setTimeofday_format(String timeofday_format) {
        this.timeofday_format = timeofday_format;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        if (id != null) {
            object.put("id", id);
        }
        if (jquery_timeofday_format != null) {
            object.put("jquery_timeofday_format", jquery_timeofday_format);
        }
        if (api_token != null) {
            object.put("api_token", api_token);
        }
        if (time_entry_retention_days != null) {
            object.put("api_token", time_entry_retention_days);
        }
        if (jquery_date_format != null) {
            object.put("jquery_date_format", jquery_date_format);
        }
        if (date_format != null) {
            object.put("date_format", date_format);
        }
        if (default_workspace_id != null) {
            object.put("default_workspace_id", default_workspace_id);
        }
        if (new_time_entries_start_automatically != null) {
            object.put("new_time_entries_start_automatically", new_time_entries_start_automatically);
        }
        if (fullname != null) {
            object.put("fullname", fullname);
        }
        if (language != null) {
            object.put("language", language);
        }
        if (beginning_of_week != null) {
            object.put("beginning_of_week", beginning_of_week);
        }
        if (timeofday_format != null) {
            object.put("timeofday_format", timeofday_format);
        }
        if (email != null) {
            object.put("email", email);
        }

        return object;
    }

    public String toJSONString() {
        return this.toJSONObject().toJSONString();
    }
}
