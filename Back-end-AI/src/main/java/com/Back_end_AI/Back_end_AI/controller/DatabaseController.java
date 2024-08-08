package com.Back_end_AI.Back_end_AI.controller;

import com.Back_end_AI.Back_end_AI.model.DatabaseParams;
import com.Back_end_AI.Back_end_AI.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connect-db")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @PostMapping
    public Object connectDatabase(@RequestBody DatabaseParams databaseParams) {
        return databaseService.getDatabaseSchema(databaseParams);
    }
}

