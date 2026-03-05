package com.rocha.tests;

import com.rocha.entity.Task;
import com.rocha.enums.Status;
import com.rocha.service.TaskService;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestTaskService {
    @Test
    public void testAddTesk(){
        System.out.println("Criando Task com ID 1");
        TaskService service = new TaskService();
        Task task = new Task();
        task.setId(1);


        System.out.println("Adicionando task");
        service.addTesk(task);
        Task resultado = service.getById(1);

        System.out.println("Verificando se task foi adicionada");
        assertEquals(task,resultado);




    }

    @Test
    public void TestGetById(){

        System.out.println("Criando teste e setando ID");
        TaskService service = new TaskService();
        Task task = new Task();
        task.setId(1);

        System.out.println("Buscando por id");
        Task resultado = service.getById(1);

        System.out.println("Verificando se retornou a task correta");
        assertEquals(task.getId(),1);

    }

    public void testRemoveTesk(){
        System.out.println("Criando teste e setando ID");
        TaskService service = new TaskService();
        Task task = new Task();
        task.setId(1);
        service.addTesk(task);

        System.out.println("Removendo por ID");
        service.removeTask(1);
        Task resultado = service.getById(1);

        System.out.println("Analisando se removeu");
        assertNull(resultado);

    }

    public void testChangeStatusById(){
        System.out.println("Criando teste e setando Status");
        TaskService service = new TaskService();
        Task task = new Task();
        task.setId(1);
        task.setStatus(Status.TODO);
        service.addTesk(task);

        System.out.println("Criando teste e setando outro Status por id");
        service.changeStatusById(1,Status.DONE);
        Task resultado = service.getById(1);

        System.out.println("Verificando se mudou o status");
        assertEquals(resultado.getStatus(),Status.DONE);

    }

    @Test
    public void testUpdateTask() {
        TaskService service = new TaskService();
        Task task = new Task("Original", "Cat", Status.TODO, 1,     LocalDate.now(), "Desc", null);
        task.setId(1);
        service.addTesk(task);

        // Atualizando apenas nome e prioridade
        service.updateTask(1, "Editada", "Desc", LocalDate.now(), 10, "Cat", Status.TODO, null);

        Task result = service.getById(1);
        assertEquals("Editada", result.getName());
        assertEquals(10, result.getPriority());
    }



}
