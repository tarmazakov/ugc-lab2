package ru.mephi.ugc.burndown.gui;

import org.primefaces.event.RowEditEvent;
import ru.mephi.ugc.burndown.interfaces.TaskService;
import ru.mephi.ugc.burndown.model.Task;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "item")
@SessionScoped
public class TaskView implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private Integer complexity;
    private String status;
    private List<Task> taskList;

    @EJB
    private TaskService service;

    @PostConstruct
    public void init() {
        taskList = service.getTasksFromDB();
    }

    public List<String> getStatuses() {
        return service.getStatuses();
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void addAction() {
        service.addTask(this.name, this.complexity, this.status);
        FacesMessage msg = new FacesMessage("Task Added", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        taskList = service.getTasksFromDB();
    }

    public void onEdit(RowEditEvent event) {
        Task task = (Task) event.getObject();
        if (name != null) {
            task.setName(name);
        }
        if (complexity != null) {
            task.setComplexity(complexity);
        }
        if (status != null) {
            task.setStatus(status);
        }
        service.editTask(task);
        Integer id = ((Task) event.getObject()).getId();
        FacesMessage msg = new FacesMessage("Task " + id + " edited", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        service.deleteTask((Task) event.getObject());
        Integer id = ((Task) event.getObject()).getId();
        FacesMessage msg = new FacesMessage("Task " + id + " deleted", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        taskList = service.getTasksFromDB();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}