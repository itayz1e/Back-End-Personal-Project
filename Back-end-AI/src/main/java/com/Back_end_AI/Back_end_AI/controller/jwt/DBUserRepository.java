package com.Back_end_AI.Back_end_AI.controller.jwt;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBUserRepository extends JpaRepository<DBUser, Long> {
    Optional<DBUser> findByUsername(String username);
}