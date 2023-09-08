package com.tgbotrix.Firstbot.model;


import jakarta.persistence.*;
import org.glassfish.grizzly.http.util.TimeStamp;

import java.util.List;

@Entity
@Table(name = "users_of_bot")
public class User {
    //jdbc:postgresql://localhost:5432/tg1_db
    @Id
    @Column(name = "chatid")
    private Long chatId;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "username")
    private String userName;
    @Column(name = "registeredat")
    private TimeStamp registeredAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tasksid") // указывает, что данный столбец в объекте-владельце
    private List<Tasks> tasks;    //  ссылается на первичный ключ в ссылочном объекте

    public User() {
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TimeStamp getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(TimeStamp registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public String toString() {
        return "User{" +
                ", chatId=" + chatId +
                ", firstname='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
