package com.Back_end_AI.Back_end_AI.controller;

import com.Back_end_AI.Back_end_AI.service.AiService;
import com.Back_end_AI.Back_end_AI.service.AppUserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Ask")
public class AiController {

    @Autowired
    private AiService aiService;

    @Autowired
    private AppUserService userService;

    private final Gson gson = new Gson();

    @GetMapping("/askChatGPT")
    public ResponseEntity<String> executeQuery(@RequestParam String userInput) {
        try {
            String sqlQuery = aiService.generateSQLQuery(userInput);
            List<Object[]> results = userService.executeCustomQuery(sqlQuery);
            String response = gson.toJson(results);
            aiService.saveCallHistory(userInput, response);
            return new ResponseEntity<>(sqlQuery, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson("Error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
