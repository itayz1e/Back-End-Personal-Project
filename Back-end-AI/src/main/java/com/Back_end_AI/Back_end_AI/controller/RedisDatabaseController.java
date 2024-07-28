package com.Back_end_AI.Back_end_AI.controller;


import com.Back_end_AI.Back_end_AI.service.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
    @RequestMapping("/api")
    public class RedisDatabaseController {

    @Autowired
    Redis redis;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/get-db-params")
    public ResponseEntity<Map<Object, Object>> getDatabaseParams() {
        Map<Object, Object> dbParams = redisTemplate.opsForHash().entries("db:params");
        return ResponseEntity.ok(dbParams);
    }

    @RequestMapping(value = "connect-db", method = RequestMethod.POST) // השתמש ב-POST אם אתה שולח נתונים בגוף הבקשה
    public Map<String, String> setKey(@RequestBody Map<String, String> dbParams) {
        String url = dbParams.get("url");
        String username = dbParams.get("username");
        String password = dbParams.get("password");

        // שמירה של הפרמטרים ב-Redis כ-hash
        redisTemplate.opsForHash().put("db:params", "url", url);
        redisTemplate.opsForHash().put("db:params", "username", username);
        redisTemplate.opsForHash().put("db:params", "password", password);

        // החזרת אישור שהנתונים נשמרו
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Database parameters saved successfully");

        return response;
    }



    @RequestMapping(value = "getKey", method = RequestMethod.GET)
    public ResponseEntity<Map<Object, Object>> getKey(@RequestParam String hashKey) {
        // קבלת כל הערכים מה-Redis ב-hash
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(hashKey);

        // בדיקה אם ה-hash קיים
        if (!entries.isEmpty()) {
            return ResponseEntity.ok(entries);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Hash key not found"));
        }
    }



}