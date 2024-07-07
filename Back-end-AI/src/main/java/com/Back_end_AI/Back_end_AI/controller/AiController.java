package com.Back_end_AI.Back_end_AI.controller;

import com.Back_end_AI.Back_end_AI.service.AiService;
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

    @GetMapping("/checkDatabaseConnection")
    public ResponseEntity<String> checkDatabaseConnection(
            @RequestParam String dbUrl,
            @RequestParam String username,
            @RequestParam String password) {
        try {
            String chatGPTResponse = aiService.checkDatabaseConnection(dbUrl, username, password);
            return new ResponseEntity<>(chatGPTResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
