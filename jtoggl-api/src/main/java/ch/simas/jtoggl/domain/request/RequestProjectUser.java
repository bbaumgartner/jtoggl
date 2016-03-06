package ch.simas.jtoggl.domain.request;

import ch.simas.jtoggl.domain.ProjectUser;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by vranikp on 4.3.16.
 *
 * @author vranikp
 */
@XmlRootElement(name = "client")
public class RequestProjectUser {
    private ProjectUser project_user;

    public RequestProjectUser() {
    }

    public RequestProjectUser(ProjectUser task) {
        this.project_user = task.clone();
        if (this.project_user.getProjectId() == null && this.project_user.getProject() != null) {
            this.project_user.setProjectId(task.getProject().getId());
        }
        if (this.project_user.getUserId() == null && this.project_user.getUser() != null) {
            this.project_user.setUserId(task.getUser().getId());
        }
        this.project_user.setProject(null);
        this.project_user.setUser(null);
        this.project_user.setData(null);
    }

    public ProjectUser getProject_user() {
        return project_user;
    }

    public void setProject_user(ProjectUser client) {
        this.project_user = client;
    }

}
