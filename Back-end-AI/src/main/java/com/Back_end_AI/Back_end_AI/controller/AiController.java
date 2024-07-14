package com.Back_end_AI.Back_end_AI.controller;


import com.Back_end_AI.Back_end_AI.service.AiService;
import com.Back_end_AI.Back_end_AI.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/Ask")
public class AiController {
    @Autowired
    private AiService aiService;
    @Autowired
    private AppUserService userService;

    @GetMapping("/askChatGPT")
    public ResponseEntity<?> executeQuery(@RequestParam String userInput) {
        try {
            String sqlQuery = aiService.generateSQLQuery(userInput);
            List<Object[]> results = userService.executeCustomQuery(sqlQuery);
            String response = results.toString();
            aiService.saveCallHistory(userInput, response);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
}