package com.Back_end_AI.Back_end_AI.controller;

import com.Back_end_AI.Back_end_AI.service.AiService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Ask")
public class AiController {

    @Autowired
    private AiService aiService;

    private final Gson gson = new Gson();

    @GetMapping("/askChatGPT")
    public ResponseEntity<String> executeQuery(@RequestParam String userInput) {
        try {
            // יצירת השאילתא באמצעות ChatGPT
            String sqlQuery = aiService.generateSQLQuery(userInput);

            // שמירה של השאלה והשאילתא שנוצרה
            aiService.saveCallHistory(userInput, sqlQuery);

            // החזרת השאילתא שנוצרה
            return new ResponseEntity<>(sqlQuery, HttpStatus.OK);
        } catch (Exception e) {
            // טיפול בשגיאות
            return new ResponseEntity<>(gson.toJson("Error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
