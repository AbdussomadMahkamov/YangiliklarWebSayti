package com.example.yangiliklarwebsaytibackend.repository;

import com.example.yangiliklarwebsaytibackend.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    Optional<Comments> findByIdAndYaratuvchiId(Integer id, Integer yaratuvchiId);
}
