package ch.simas.jtoggl.domain.request;

import ch.simas.jtoggl.domain.Task;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by vranikp on 4.3.16.
 *
 * @author vranikp
 */
@XmlRootElement(name = "client")
public class RequestTask {
    private Task task;

    public RequestTask() {
    }

    public RequestTask(Task task) {
        this.task = task.clone();
        if (this.task.getProjectId() == null && this.task.getProject() != null) {
            this.task.setProjectId(task.getProject().getId());
        }
        this.task.setWorkspace(null);
        this.task.setProject(null);
        this.task.setUser(null);
        this.task.setData(null);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task client) {
        this.task = client;
    }

}
