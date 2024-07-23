package com.Back_end_AI.Back_end_AI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class QueryService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Connection getDatabaseConnection() {
        String url = (String) redisTemplate.opsForHash().get("db:params", "url");
        String username = (String) redisTemplate.opsForHash().get("db:params", "username");
        String password = (String) redisTemplate.opsForHash().get("db:params", "password");

        try {
            // Initialize and return the database connection using these parameters
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            // Log and handle the SQL exception
            e.printStackTrace(); // You might want to log this using a logging framework
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }
}

