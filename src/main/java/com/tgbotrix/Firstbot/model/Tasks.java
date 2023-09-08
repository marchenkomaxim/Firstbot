package com.tgbotrix.Firstbot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String taskName;
    private boolean complete;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tasks() {
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taksName) {
        this.taskName = taksName;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", complete=" + complete +
                '}';
    }
}
