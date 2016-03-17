package ch.simas.jtoggl.domain;

/**
 * Contract to have reference to Workspace.
 */
public interface WithWorkspace {

    /**
     * @return Workspace object.
     */
    Workspace getWorkspace();

    void setWorkspace(Workspace workspace);

    /**
     * @return Workspace ID.
     */
    Long getWorkspaceId();

    void setWorkspaceId(Long workspaceId);
}
