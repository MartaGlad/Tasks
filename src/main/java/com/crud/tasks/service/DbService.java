package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {
    private final TaskRepository repository;

    public List<Task> getAllTasks() { //"opakowanie" dla wywołania metody findAll() z TaskRepository
        return repository.findAll();  //domyślnie findAll() z CrudRepository zwraca Iterable<T>
    }

    public Optional<Task> getTaskById(final Long id) {
        return repository.findById(id);
    }

}
