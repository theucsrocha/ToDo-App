package com.rocha.app;

import com.rocha.enums.Status;
import com.rocha.service.TaskService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        TaskService service = new TaskService();
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter alarmFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
            System.out.println("0 - Exit");
            System.out.print("Choose an option: ");

            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Description: ");
                    String description = scanner.nextLine();

                    System.out.print("Expiration (yyyy-MM-dd): ");
                    LocalDate expiration = LocalDate.parse(scanner.nextLine());

                    System.out.print("Priority (number): ");
                    int priority = Integer.parseInt(scanner.nextLine());

                    System.out.print("Category: ");
                    String category = scanner.nextLine();

                    System.out.print("Status (TODO, DOING, DONE): ");
                    Status status = Status.valueOf(scanner.nextLine().trim().toUpperCase());

                    LocalDateTime alarmTime = null;

                    System.out.print("Do you want an alarm? (y/n): ");
                    String wantAlarm = scanner.nextLine().trim().toLowerCase();

                    if (wantAlarm.equals("y")) {
                        System.out.print("Alarm time (yyyy-MM-dd HH:mm): ");
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

                case 2:
                    service.showAllTasks();
                    break;

                case 3:
                    System.out.print("Enter id to remove: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    service.removeTask(id);
                    System.out.println("Remove operation executed.");
                    break;

                case 4:
                    System.out.print("Enter priority: ");
                    int p = Integer.parseInt(scanner.nextLine());
                    service.listByPriority(p);
                    break;

                case 5:
                    System.out.print("Enter category: ");
                    String c = scanner.nextLine();
                    service.listByCategory(c);
                    break;

                case 6:
                    System.out.print("Enter status (TODO, DOING, DONE): ");
                    Status s = Status.valueOf(scanner.nextLine().trim().toUpperCase());
                    service.listByStatus(s);
                    break;

                case 7:
                    System.out.print("Enter task id: ");
                    int taskId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter new status (TODO, DOING, DONE): ");
                    Status newStatus = Status.valueOf(scanner.nextLine().trim().toUpperCase());

                    service.changeStatusById(taskId, newStatus);
                    System.out.println("Status updated.");
                    break;

                case 0:
                    System.out.println("Bye.");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();

    }

}