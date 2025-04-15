package com.example.todo.tag.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.todo.tag.model.Tag;
import com.example.todo.tag.model.TagRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> saveOrGet(List<String> tags) {
        List<Tag> result = new ArrayList<>();

        tags.forEach(tag -> {
            Optional<Tag> value = tagRepository.findByText(tag);
            result.add(value.orElseGet(() -> tagRepository.save(Tag.builder().text(tag).build())));
        });

        return result;
    }
}
