package com.jojoidu.book.web;

import com.jojoidu.book.constants.TaskStatus;
import com.jojoidu.book.model.Task;
import com.jojoidu.book.service.TaskService;
import com.jojoidu.book.web.vo.ResultResponse;
import com.jojoidu.book.web.vo.TaskRequest;
import com.jojoidu.book.web.vo.TaskStatusRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * 새로운 할일 추가
     * @param req 추가하고자 하는 일
     * @return 추가된 할일
     */

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest req) {
        var result = this.taskService.add(req.getTitle(), req.getDescription(), req.getDueDate());
        return ResponseEntity.ok(result);
    }

    /**
     * 특정 마감일에 해당하는 할일 목록을 반환
     *
     * @param dueDate 할일의 마감일
     * @return 마감일에 해당하는 할일 목록
     */
    @GetMapping
    public ResponseEntity<List<Task>> getTask(Optional<String> dueDate) {
        List<Task> result;

        if (dueDate.isPresent()) {  // 마감일이 있는 경우
            result = this.taskService.getByDueDate(dueDate.get());
        } else {    // 마감일이 없는 경우
            result = this.taskService.getAll();
        }

        return ResponseEntity.ok(result);   // ok메소드는 상태코드 200을 반환함.(클라이언트 오류는 400번대, 서버오류는 500번대)
    }

    /**
     * 새로운 할일 추가
     *
     * @param id 할일 ID
     * @return ID에 해당하는 할일 객체
     */
    @GetMapping("/{id}")    // annotation된 함수의 parameter로 id가 들어가 있는데 해당 id로 맵핑해준다.
    public ResponseEntity<Task> fetchOneTask(@PathVariable long id) {
        var result = this.taskService.getOne(id);
        return ResponseEntity.ok(result);
    }

    /**
     * 특정 상태에 해당하는 할일 목록을 반환
     *
     * @param status 할일 상태
     * @return 상태에 해당하는 할일 목록
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getByStatus(@PathVariable TaskStatus status) {
        var result = this.taskService.getByStatus(status);
        return ResponseEntity.ok(result);
    }

    /**
     * 특정 ID에 해당하는 할일을 수정
     *
     * @param id 할일 ID
     * @param task 수정할 할일 정보
     * @return 수정된 할일 객체
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id,
                                           @RequestBody TaskRequest task) {
        var result = this.taskService.update(id,
                                    task.getTitle(),
                                    task.getDescription(),
                                    task.getDueDate());
        return ResponseEntity.ok(result);
    }

    /**
     * 특정 ID에 해당하는 할일의 상태를 수정
     *
     * @param id 할일 ID
     * @param req 수정할 할일 상태 정보
     * @return 수정된 할일 객체
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id,
                                                 @RequestBody TaskStatusRequest req) {
        var result = this.taskService.updateStatus(id, req.getStatus());
        return ResponseEntity.ok(result);
    }

    /**
     * 특정 ID에 해당하는 할일을 삭제
     *
     * @param id 삭제할 할일 ID
     * @return 삭제 결과를 담은 응답 객체
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse> deleteTask(@PathVariable Long id) {
        var result = this.taskService.delete(id);
        return ResponseEntity.ok(new ResultResponse(result));
    }

    // 추가 기능
    // 사용자 입장에서 task의 상태를 변경하기 위해 모든 상태를 조회할 수 있는 편의성을 제공
    @GetMapping("/status")
    public ResponseEntity<TaskStatus[]> getAllStatus() {
        var status = TaskStatus.values();
        return ResponseEntity.ok(status);
    }

}
