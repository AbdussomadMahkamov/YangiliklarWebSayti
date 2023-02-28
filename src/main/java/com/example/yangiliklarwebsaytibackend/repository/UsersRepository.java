package com.example.yangiliklarwebsaytibackend.repository;

import com.example.yangiliklarwebsaytibackend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    boolean existsByUsername(String username);
    boolean existsByTelRaqam(String telRaqam);
    Optional<Users> findByUsernameAndEmailKod(String username, String emailKod);
    Optional<Users> findByUsername(String username);
}
