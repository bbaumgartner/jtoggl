package ch.simas.jtoggl;

public class Project {

    private Long id;
    private String name;
    private String client_project_name;

    public String getClient_project_name() {
        return client_project_name;
    }

    public void setClient_project_name(String client_project_name) {
        this.client_project_name = client_project_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", name=" + name + ", client_project_name=" + client_project_name + '}';
    }
}
