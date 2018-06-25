package ch.simas.jtoggl;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class PagedResult {

    private Long totalCount;

    private Long pageSize;

    private List<TimeEntry> entries;

    public PagedResult(String jsonString) {
        JSONObject object = (JSONObject) JSONValue.parse(jsonString);

        totalCount = (Long) object.get("total_count");
        pageSize = (Long) object.get("per_page");

        JSONArray data = (JSONArray) object.get("data");
        entries = new ArrayList<>();
        if (data != null) {
            for (Object entry : data) {
                entries.add(new TimeEntry(((JSONObject)entry).toJSONString()));
            }
        }
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public List<TimeEntry> getEntries() {
        return entries;
    }

}