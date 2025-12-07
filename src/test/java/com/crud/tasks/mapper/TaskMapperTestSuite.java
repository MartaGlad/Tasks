package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "t1", "c1");
        //When
        Task mappedTask = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(1L, mappedTask.getId());
        assertEquals("t1", mappedTask.getTitle());
        assertEquals("c1", mappedTask.getContent());
    }

    @Test
    void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "t1", "c1");
        //When
        TaskDto mappedTaskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(1L, mappedTaskDto.getId());
        assertEquals("t1", mappedTaskDto.getTitle());
        assertEquals("c1", mappedTaskDto.getContent());
    }

    @Test
    void testMapToTaskDtoList() {
        //Given
        Task t1 = new Task(1L, "t1", "c1");
        Task t2 = new Task(2L, "t2", "c2");
        Task t3 = new Task(3L, "t3", "c3");
        List<Task> taskList = new ArrayList<>();
        Collections.addAll(taskList, t1, t2, t3);
        //When
        List<TaskDto> mappedTaskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertEquals(taskList.size(), mappedTaskDtoList.size());
        assertEquals(3, mappedTaskDtoList.size());
        assertEquals("t2", mappedTaskDtoList.get(1).getTitle());
    }
}
