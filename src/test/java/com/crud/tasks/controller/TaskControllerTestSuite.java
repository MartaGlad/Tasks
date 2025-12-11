package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@WebMvcTest(TaskController.class)
class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DbService service;

    @MockitoBean
    private TaskMapper taskMapper;

    @Test
    void testGetAllTasks() throws Exception {
        //Given
        List<Task> tasks = new ArrayList<>();
        Task task0 = new Task(10L, "t0", "c0");
        Task task1 = new Task(11L, "t1", "c1");
        Task task2 = new Task(12L, "t2", "c2");
        Collections.addAll(tasks, task0, task1, task2);

        List<TaskDto> tasksDto = new ArrayList<>();
        TaskDto taskDto0 = new TaskDto(10L, "t0", "c0");
        TaskDto taskDto1 = new TaskDto(11L, "t1", "c1");
        TaskDto taskDto2 = new TaskDto(12L, "t2", "c2");
        Collections.addAll(tasksDto, taskDto0, taskDto1, taskDto2);

        when(service.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(tasksDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title", Matchers.is("t2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("c1")));
    }

    @Test
    void testGetOneTask() throws Exception {
        //Given
        Task task1 = new Task(11L, "t1", "c1");
        TaskDto taskDto1 = new TaskDto(11L, "t1", "c1");
        when(service.getTask(11L)).thenReturn(task1);
        when(taskMapper.mapToTaskDto(task1)).thenReturn(taskDto1);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tasks/" + task1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(11)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(task1.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("c1")));
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() throws Exception {
        //Given
        doThrow(new TaskNotFoundException()).when(service).getTask(1L);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testDeleteTask() throws Exception {
        //Given, When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service).deleteTask(1L);
    }

    @Test
    void testUpdateTask() throws Exception {
        //Given
        TaskDto taskDtoRequest = new TaskDto(1L, "t", "c");
        Task mappedTask = new Task(1L, "t", "c");
        Task savedTask = new Task(1L, "tUpdated", "cUpdated");
        TaskDto taskDtoResponse = new TaskDto(1L, "tUpdated", "cUpdated");
        when(taskMapper.mapToTask(Mockito.any(TaskDto.class))).thenReturn(mappedTask);
        when(service.saveTask(mappedTask)).thenReturn(savedTask);
        when(taskMapper.mapToTaskDto(savedTask)).thenReturn(taskDtoResponse);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDtoRequest);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("tUpdated")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("cUpdated")));
    }

    @Test
    void testCreateTask() throws Exception {
        //Given
        TaskDto taskDtoRequest = new TaskDto(5L, "t", "c");
        Task mappedTask = new Task(5L, "t", "c");
        Task savedTask = new Task(5L, "t", "c");
        when(taskMapper.mapToTask(Mockito.any(TaskDto.class))).thenReturn(mappedTask);
        when(service.saveTask(mappedTask)).thenReturn(savedTask);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDtoRequest);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                        .andExpect(MockMvcResultMatchers.status().is(200));

        verify(taskMapper, times(1)).mapToTask(Mockito.any(TaskDto.class));
        verify(service, times(1)).saveTask(mappedTask);
    }
}