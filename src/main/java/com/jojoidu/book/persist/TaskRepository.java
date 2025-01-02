package com.jojoidu.book.persist;

import com.jojoidu.book.constants.TaskStatus;
import com.jojoidu.book.persist.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllByDueDate(Date dueDate);

    List<TaskEntity> findAllByStatus(TaskStatus status);
}
