package com.example.todo.task.dto;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import com.example.todo.tag.model.Tag;
import com.example.todo.task.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EditTaskRequest extends CreateTaskRequest {

    private Long id;
    private boolean completed;
    private LocalDateTime createdAt;
    private String username;

    public static EditTaskRequest of(Task task) {
        return EditTaskRequest.builder().id(task.getId()).completed(task.isCompleted())
                .createdAt(task.getCreatedAt()).username(task.getAuthor().getFullname())
                .title(task.getTitle()).description(task.getDescription())
                .categoryId(task.getCategory().getId())
                .tags(task.getTags().stream().map(Tag::getText).collect(Collectors.joining(",")))
                .build();
    }


}
