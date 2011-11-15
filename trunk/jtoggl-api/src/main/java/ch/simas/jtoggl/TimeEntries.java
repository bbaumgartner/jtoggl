package ch.simas.jtoggl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class TimeEntries {

    private List<TimeEntry> entries = new ArrayList<TimeEntry>();
    private Date related_data_updated_at;

    TimeEntries(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);
        this.related_data_updated_at = DateUtil.convertStringToDate((String) object.get("related_data_updated_at"));

        JSONArray data = (JSONArray) object.get("data");
        for (Object objectEntry : data) {
            TimeEntry timeEntry = new TimeEntry(((JSONObject) objectEntry).toJSONString());
            this.entries.add(timeEntry);
        }
    }

    public List<TimeEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<TimeEntry> entries) {
        this.entries = entries;
    }

    public Date getRelated_data_updated_at() {
        return related_data_updated_at;
    }

    public void setRelated_data_updated_at(Date related_data_updated_at) {
        this.related_data_updated_at = related_data_updated_at;
    }

    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        for (TimeEntry timeEntry : this.entries) {
            array.add(timeEntry.toJSONObject());
        }
        object.put("data", array);

        object.put("related_data_updated_at", DateUtil.convertDateToString(related_data_updated_at));

        return object;
    }
    
    public String toJSONString() {
        return this.toJSONObject().toJSONString();
    }

    @Override
    public String toString() {
        return "TimeEntries{" + "entries=" + entries + ", related_data_updated_at=" + related_data_updated_at + '}';
    }
}
