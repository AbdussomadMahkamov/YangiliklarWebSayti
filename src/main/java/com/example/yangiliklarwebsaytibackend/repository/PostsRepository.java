package com.example.yangiliklarwebsaytibackend.repository;

import com.example.yangiliklarwebsaytibackend.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Integer> {
    Optional<Posts> findBySarlavha(String sarlavha);
    Optional<Posts> findByIdAndYaratuvchiId(Integer id, Integer yaratuvchiId);
}
