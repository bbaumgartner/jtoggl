package ch.simas.jtoggl.domain;

/**
 * Created by hpa on 17.3.16.
 */
public interface WithWorkspace {
    Workspace getWorkspace();

    void setWorkspace(Workspace workspace);

    Long getWorkspaceId();

    void setWorkspaceId(Long workspaceId);
}
