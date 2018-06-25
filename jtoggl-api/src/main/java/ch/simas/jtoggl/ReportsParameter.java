package ch.simas.jtoggl;

import java.util.Set;
import java.util.stream.Collectors;

public class ReportsParameter {

    private final long workspaceId;
    private final String userAgentName;
    private String since;
    private String until;
    private Set<Long> projectIds;

    public ReportsParameter(long workspaceId, String userAgentName) {
        this.workspaceId = workspaceId;
        this.userAgentName = userAgentName;
    }

    public long getWorkspaceId() {
        return workspaceId;
    }

    public String getUserAgentName() {
        return userAgentName;
    }

    public String getSince() {
        return since;
    }

    public ReportsParameter setSince(String since) {
        this.since = since;
        return this;
    }

    public String getUntil() {
        return until;
    }

    public ReportsParameter setUntil(String until) {
        this.until = until;
        return this;
    }

    public Set<Long> getProjectIds() {
        return projectIds;
    }

    public ReportsParameter setProjectIds(Set<Long> projectIds) {
        this.projectIds = projectIds;
        return this;
    }

    public String toParamList() {
        StringBuilder result = new StringBuilder();

        result.append("workspace_id=").append(workspaceId);
        result.append("&user_agent=").append(userAgentName);
        if (since != null)
            result.append("&since=").append(since);
        if (until != null)
            result.append("&until=").append(until);
        if (projectIds != null)
            result.append("&project_ids=").append(projectIds.stream().map(String::valueOf).collect(Collectors.joining(",")));

        return result.toString();
    }
}
