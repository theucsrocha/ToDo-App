package com.rocha.app;

import com.rocha.entity.Task;
import com.rocha.enums.Status;
import com.rocha.service.TaskService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static final DateTimeFormatter alarmFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        TaskService service = new TaskService();
        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("\n=== TASK MENU ===");
            System.out.println("1 - Add task");
            System.out.println("2 - List all tasks");
            System.out.println("3 - Remove task by id");
            System.out.println("4 - List by priority");
            System.out.println("5 - List by category");
            System.out.println("6 - List by status");
            System.out.println("7 - Change status by id");
            System.out.println("8 - Update task");
            System.out.println("0 - Exit");
            System.out.println("Choose an option: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (option) {
                case 1: {
                    System.out.println("Name: ");
                    String name = scanner.nextLine();

                    System.out.println("Description: ");
                    String description = scanner.nextLine();

                    System.out.println("Expiration (yyyy-MM-dd): ");
                    LocalDate expiration = LocalDate.parse(scanner.nextLine());

                    System.out.println("Priority (number): ");
                    int priority = Integer.parseInt(scanner.nextLine());

                    System.out.println("Category: ");
                    String category = scanner.nextLine();

                    System.out.println("Status (TODO, DOING, DONE): ");
                    Status status = Status.valueOf(scanner.nextLine().trim().toUpperCase());

                    LocalDateTime alarmTime = null;

                    System.out.println("Do you want an alarm? (y/n): ");
                    String wantAlarm = scanner.nextLine().trim().toLowerCase();

                    if (wantAlarm.equals("y")) {
                        System.out.println("Alarm time (yyyy-MM-dd HH:mm): ");
                        try {
                            alarmTime = LocalDateTime.parse(scanner.nextLine(), alarmFormatter);
                        } catch (Exception e) {
                            System.out.println("Invalid date/time format. Alarm will be ignored.");
                            alarmTime = null;
                        }
                    }

                    service.addTesk(name, description, expiration, priority, category, status, alarmTime);
                    System.out.println("Task added.");
                    break;
                }
                case 2: {
                    service.showAllTasks();
                    break;
                }
                case 3: {
                    System.out.println("Enter id to remove: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    service.removeTask(id);
                    System.out.println("Remove operation executed.");
                    break;
                }
                case 4: {
                    System.out.println("Enter priority: ");
                    int priority = Integer.parseInt(scanner.nextLine());
                    service.listByPriority(priority);
                    break;
                }
                case 5: {
                    System.out.println("Enter category: ");
                    String category = scanner.nextLine();
                    service.listByCategory(category);
                    break;
                }
                case 6: {
                    System.out.println("Enter status (TODO, DOING, DONE): ");
                    Status status = Status.valueOf(scanner.nextLine().trim().toUpperCase());
                    service.listByStatus(status);
                    break;
                }
                case 7: {
                    System.out.println("Enter task id: ");
                    int taskId = Integer.parseInt(scanner.nextLine());

                    System.out.println("Enter new status (TODO, DOING, DONE): ");
                    Status newStatus = Status.valueOf(scanner.nextLine().trim().toUpperCase());

                    service.changeStatusById(taskId, newStatus);
                    System.out.println("Status updated.");
                    break;
                }
                case 8: {
                    System.out.print("Enter id to update: ");
                    int updateId = Integer.parseInt(scanner.nextLine());
                    Task existing = service.getById(updateId);

                    if (existing == null) {
                        System.out.println("Task not found.");
                        break;
                    }

                    System.out.println("--- Update Task (Press ENTER to keep current value) ---");

                    // Lógica com ternário para o Nome
                    System.out.print("Name [" + existing.getName() + "]: ");
                    String nameInput = scanner.nextLine();
                    String newName = nameInput.isEmpty() ? existing.getName() : nameInput;

                    // Lógica com ternário para a Descrição
                    System.out.print("Description [" + existing.getDescription() + "]: ");
                    String descInput = scanner.nextLine();
                    String newDesc = descInput.isEmpty() ? existing.getDescription() : descInput;

                    // Lógica com ternário para a Data de Expiração
                    System.out.print("Expiration [" + existing.getExpiration() + "] (yyyy-MM-dd): ");
                    String expStr = scanner.nextLine();
                    LocalDate newExp = expStr.isEmpty() ? existing.getExpiration() : LocalDate.parse(expStr);

                    // Lógica com ternário para a Prioridade
                    System.out.print("Priority [" + existing.getPriority() + "]: ");
                    String pStr = scanner.nextLine();
                    int newPrio = pStr.isEmpty() ? existing.getPriority() : Integer.parseInt(pStr);

                    // Lógica com ternário para a Categoria
                    System.out.print("Category [" + existing.getCategory() + "]: ");
                    String catInput = scanner.nextLine();
                    String newCat = catInput.isEmpty() ? existing.getCategory() : catInput;

                    // Lógica com ternário para o Status
                    System.out.print("Status [" + existing.getStatus() + "] (TODO, DOING, DONE): ");
                    String sStr = scanner.nextLine().trim().toUpperCase();
                    Status newStat = sStr.isEmpty() ? existing.getStatus() : Status.valueOf(sStr);

                    // Lógica do Alarme
                    LocalDateTime newAlarm = existing.getAlarmTime();
                    System.out.print("Update alarm? (y/n) [Current: " + (newAlarm != null ? newAlarm : "None") + "]: ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                        System.out.print("New Alarm time (yyyy-MM-dd HH:mm): ");
                        try {
                            newAlarm = LocalDateTime.parse(scanner.nextLine(), alarmFormatter);
                        } catch (Exception e) {
                            System.out.println("Invalid format. Keeping old alarm.");
                        }
                    }

                    service.updateTask(updateId, newName, newDesc, newExp, newPrio, newCat, newStat, newAlarm);
                    System.out.println("Task updated successfully!");
                    break;
                }
                case 0: {
                    System.out.println("Bye.");
                    break;
                }
                default: {
                    System.out.println("Invalid option.");
                }
            }
        }
        scanner.close();
    }








}