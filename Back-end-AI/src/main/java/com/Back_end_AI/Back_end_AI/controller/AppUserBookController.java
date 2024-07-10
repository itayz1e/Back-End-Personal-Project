package com.Back_end_AI.Back_end_AI.controller;

import com.Back_end_AI.Back_end_AI.model.AppUserBook;
import com.Back_end_AI.Back_end_AI.service.AppUserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appUserBook")
public class AppUserBookController {

    @Autowired
    private AppUserBookService appUserBookService;

    @PostMapping
    public AppUserBook createAppUserBook(@RequestBody AppUserBook appUserBook) {
        return appUserBookService.saveAppUserBook(appUserBook);
    }

    @GetMapping
    public List<AppUserBook> getAllAppUserBooks() {
        return appUserBookService.getAllAppUserBooks();
    }
}
