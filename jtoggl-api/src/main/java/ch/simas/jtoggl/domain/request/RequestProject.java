package ch.simas.jtoggl.domain.request;

import ch.simas.jtoggl.domain.Project;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by vranikp on 4.3.16.
 *
 * @author vranikp
 */
@XmlRootElement(name = "client")
public class RequestProject {
    private Project project;

    public RequestProject() {
    }

    public RequestProject(Project project) {
        this.project = project.clone();
        if (this.project.getWid() == null && this.project.getWorkspace() != null) {
            this.project.setWid(this.project.getWorkspace().getId());
        }
        this.project.setWorkspace(null);
        this.project.setData(null);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
