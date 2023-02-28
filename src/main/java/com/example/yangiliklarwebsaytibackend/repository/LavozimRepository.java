package com.example.yangiliklarwebsaytibackend.repository;

import com.example.yangiliklarwebsaytibackend.entity.Lavozim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LavozimRepository extends JpaRepository<Lavozim, Integer> {
    Optional<Lavozim> findByNomi(String nomi);
    boolean existsByNomi(String nomi);
}
