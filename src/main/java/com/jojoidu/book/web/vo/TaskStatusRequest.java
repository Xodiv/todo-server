package com.jojoidu.book.web.vo;


import com.jojoidu.book.constants.TaskStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TaskStatusRequest {
    private TaskStatus status;
}
