package com.jojoidu.book.service;

import com.jojoidu.book.constants.TaskStatus;
import com.jojoidu.book.model.Task;
import com.jojoidu.book.persist.TaskRepository;
import com.jojoidu.book.persist.entity.TaskEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task add(String title, String description, LocalDate dueDate) {
        var e = TaskEntity.builder()
                .title(title)
                .description(description)
                .dueDate(Date.valueOf(dueDate))
                .status(TaskStatus.TODO)
                .build();

        var saved = this.taskRepository.save(e);
        return entitiyToObject(saved);
    }

    public List<Task> getAll() {
        return this.taskRepository.findAll().stream()
                .map(this::entitiyToObject)
                .collect(Collectors.toList());
    }

    public List<Task> getByDueDate(String dueDate) {
        return this.taskRepository.findAllByDueDate(Date.valueOf(dueDate)).stream()
                .map(this::entitiyToObject)
                .collect(Collectors.toList());
    }

    public List<Task> getByStatus(TaskStatus status) {
        return this.taskRepository.findAllByStatus(status).stream()
                .map(this::entitiyToObject)
                .collect(Collectors.toList());
    }

    public Task getOne(Long id) {
        var entity = this.getById(id);
        return this.entitiyToObject(entity);
    }

    private TaskEntity getById(Long id) {
        return this.taskRepository.findById(id)     // findById(id)의 return값이 null일수도 있기 때문에 orElseThrow()를 통해 예외처리해준다.
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("not exists task id [%id]", id)));
    }

    private Task entitiyToObject(TaskEntity e) {
        return Task.builder()
                .id(e.getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .status(e.getStatus())
                .dueDate(e.getDueDate().toString())
                .createdAt(e.getCreatedAt().toLocalDateTime())
                .updatedAt(e.getUpdatedAt().toLocalDateTime())
                .build();
    }
}
