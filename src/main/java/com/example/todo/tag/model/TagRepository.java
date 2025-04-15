package com.example.todo.tag.model;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByText(String text);

}
