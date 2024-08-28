package com.Back_end_AI.Back_end_AI.controller;

import com.Back_end_AI.Back_end_AI.model.DatabaseParams;
import com.Back_end_AI.Back_end_AI.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connect-db")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @PostMapping
    public ResponseEntity<Object> connectDatabase(@RequestBody DatabaseParams databaseParams) {
        try {
            // שמירת פרטי החיבור ב-Redis
            databaseService.saveDatabaseParamsToRedis(databaseParams);

            // משיכת סכימת הנתונים ושמירתם ב-Redis
            Object result = databaseService.getDatabaseSchema(databaseParams);

            return ResponseEntity.ok("Connecting to the database was successful!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving database schema: " + e.getMessage());
        }
    }
}
