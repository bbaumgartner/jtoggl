package ch.simas.jtoggl.domain;

/**
 * Contract to have project reference.
 */
public interface WithProject {

    /**
     * @return Project object.
     */
    Project getProject();

    void setProject(Project project);

    /**
     * @return Project ID.
     */
    Long getProjectId();

    void setProjectId(Long projectId);
}
