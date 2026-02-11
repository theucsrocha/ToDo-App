package com.rocha.service;

import com.rocha.entity.Task;
import com.rocha.enums.Status;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Task> taskList;

    public TaskService() {
        this.taskList = new ArrayList<>();
    }

    public void addTesk (String name, String description, LocalDate expiration, int priority, String category, Status status){
    taskList.add(new Task(name,category,status,priority,expiration,description));
        Collections.sort(taskList);
    }

    public void showAllTasks(){
        taskList.forEach(System.out::println);
    }

    public void removeTask(int id){
        taskList.removeIf(x -> x.getId() == id);
    }

    public void listByStatus(Status status){
        taskList.stream().filter(x->x.getStatus()==status).collect(Collectors.toList()).forEach(System.out::println);

    }

    public void listByPriority(int priority){
        taskList.stream().filter(x->x.getPriority()==priority).collect(Collectors.toList()).forEach(System.out::println);

    }
    public void listByCategory(String category){
        taskList.stream().filter(x->x.getCategory().equals(category)).collect(Collectors.toList()).forEach(System.out::println);

    }

    public void changeStatusById(int id,Status status){
        taskList.stream().filter(x->x.getId()==id).collect(Collectors.toList()).forEach(x->x.setStatus(status));
    }



}

