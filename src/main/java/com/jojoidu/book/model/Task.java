package com.jojoidu.book.model;

import com.jojoidu.book.constants.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class Task {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private String dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
