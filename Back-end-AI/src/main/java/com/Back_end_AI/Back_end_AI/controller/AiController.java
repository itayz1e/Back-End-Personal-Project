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

            // ביצוע השאילתא לקבלת התוצאות
            String result = aiService.executeSQLQuery(sqlQuery);

            // החזרת התוצאות של השאילתא
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            // טיפול בשגיאות
            String errorResponse = gson.toJson(new ErrorResponse("Error", e.getMessage()));
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // מחלקת שגיאות למענה אחיד
    private static class ErrorResponse {
        private String status;
        private String message;

        public ErrorResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
