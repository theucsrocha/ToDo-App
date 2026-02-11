package com.rocha.entity;

import com.rocha.enums.Status;
import com.rocha.enums.Status;

import java.time.LocalDate;

public class Task implements Comparable<Task>
{
    static int taskCount = 0;
    int id;
    String name;
    String description;
    LocalDate expiration;
    int priority;
    Status status;
    String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task(String name, String category, Status status, int priority, LocalDate expiration, String description) {
        this.id = taskCount++;
        this.name = name;
        this.category = category;
        this.status = status;
        this.priority = priority;
        this.expiration = expiration;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public static int getTaskCount() {
        return taskCount;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", expiration=" + expiration +
                ", priority=" + priority +
                ", status=" + status +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(this.priority,o.priority);
    }
}
