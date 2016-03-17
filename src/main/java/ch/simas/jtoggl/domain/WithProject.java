package ch.simas.jtoggl.domain;

/**
 * Created by hpa on 17.3.16.
 */
public interface WithProject {
    Project getProject();

    void setProject(Project project);

    Long getProjectId();

    void setProjectId(Long projectId);
}
