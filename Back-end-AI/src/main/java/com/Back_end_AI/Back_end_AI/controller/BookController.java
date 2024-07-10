package com.Back_end_AI.Back_end_AI.controller;


import com.Back_end_AI.Back_end_AI.model.AppBook;
import com.Back_end_AI.Back_end_AI.model.AppUser;
import com.Back_end_AI.Back_end_AI.service.AppBookService;
import com.Back_end_AI.Back_end_AI.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private AppBookService appBookService;

    @PostMapping
    public AppBook createBook(@RequestBody AppBook book) {
        return appBookService.saveBook(book);
    }

    @GetMapping
    public List<AppBook> getAllBooks() {
        return appBookService.getAllBooks();
    }
}
