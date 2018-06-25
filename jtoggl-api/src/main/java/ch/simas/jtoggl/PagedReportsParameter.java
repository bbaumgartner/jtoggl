package ch.simas.jtoggl;

import java.util.Set;

public class PagedReportsParameter extends ReportsParameter {

    private int page = 1;

    public PagedReportsParameter(long workspaceId, String userAgentName) {
        super(workspaceId, userAgentName);
    }

    public int getPage() {
        return page;
    }

    public PagedReportsParameter setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public String toParamList() {
        return super.toParamList() + "&page=" + page;
    }
}
