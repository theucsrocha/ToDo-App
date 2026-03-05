package com.rocha.service;

import com.rocha.entity.Task;
import com.rocha.enums.Status;
import java.awt.Toolkit;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Task> taskList;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    // Guarda o alarme agendado por id (pra cancelar se remover/alterar)
    private final Map<Integer, ScheduledFuture<?>> alarms = new HashMap<>();

    public TaskService() {
        this.taskList = new ArrayList<>();
    }

    public void addTesk(String name, String description, LocalDate expiration, int priority, String category, Status status, LocalDateTime alarmTime) {
        Task task = new Task(name, category, status, priority, expiration, description, alarmTime);
        taskList.add(task);
        Collections.sort(taskList);

        scheduleAlarm(task); // agenda se tiver alarmTime
    }
    public void updateTask(int id, String name, String description, LocalDate expiration, int priority, String category, Status status, LocalDateTime alarmTime) {
        Task task = getById(id);
        if (task != null) {
            task.setName(name);
            task.setDescription(description);
            task.setExpiration(expiration);
            task.setPriority(priority);
            task.setCategory(category);
            task.setStatus(status);
            task.setAlarmTime(alarmTime);

            if (status == Status.DONE) {
                cancelAlarm(id);
            } else {
                scheduleAlarm(task);
            }
            Collections.sort(taskList);
        }
    }

    public void addTesk(Task task){
        taskList.add(task);
    }

    public Task getById(Integer id){
        return taskList.stream().filter(task -> task.getId() == id).findFirst().orElse(null);
    }

    public void showAllTasks(){
        taskList.forEach(System.out::println);
    }

    public void removeTask(int id) {
        cancelAlarm(id);
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

    public void changeStatusById(int id, Status status) {
        taskList.stream()
                .filter(x -> x.getId() == id)
                .forEach(x -> {
                    x.setStatus(status);
                    if (status == Status.DONE) {
                        cancelAlarm(id);
                    }
                });
    }

    private void scheduleAlarm(Task task) {
        // se não tem alarme, não faz nada
        if (task.getAlarmTime() == null) return;

        long delayMs = Duration.between(LocalDateTime.now(), task.getAlarmTime()).toMillis();

        // se o alarme está no passado, ignora
        if (delayMs <= 0) return;

        // se já tinha alarme pra esse id, cancela e agenda de novo
        cancelAlarm(task.getId());

        ScheduledFuture<?> future = scheduler.schedule(() -> {
            System.out.println("\n=================================");
            System.out.println("🔔 ALARME ATIVADO!");
            System.out.println("Tarefa: " + task.getName());
            System.out.println("Horário do alarme: " + task.getAlarmTime());
            System.out.println("=================================");
            Toolkit.getDefaultToolkit().beep();
            System.out.print("> ");
        }, delayMs, TimeUnit.MILLISECONDS);

        alarms.put(task.getId(), future);
    }

    private void cancelAlarm(int taskId) {
        ScheduledFuture<?> f = alarms.remove(taskId);
        if (f != null) f.cancel(false);
    }


}

