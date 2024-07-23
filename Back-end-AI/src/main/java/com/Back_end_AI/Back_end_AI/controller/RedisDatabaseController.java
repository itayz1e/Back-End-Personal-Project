package com.Back_end_AI.Back_end_AI.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
    @RequestMapping("/api")
    public class RedisDatabaseController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/connect-db")
    public Map<String, String> connectDatabase(@RequestBody Map<String, String> dbParams) {
        String url = dbParams.get("url");
        String username = dbParams.get("username");
        String password = dbParams.get("password");

        redisTemplate.opsForHash().put("db:params", "url", url);
        redisTemplate.opsForHash().put("db:params", "username", username);
        redisTemplate.opsForHash().put("db:params", "password", password);

        return Collections.singletonMap("message", "Database parameters stored successfully.");
    }

    @GetMapping("/get-db-params")
    public ResponseEntity<Map<Object, Object>> getDatabaseParams() {
        Map<Object, Object> dbParams = redisTemplate.opsForHash().entries("db:params");
        return ResponseEntity.ok(dbParams);
    }
}