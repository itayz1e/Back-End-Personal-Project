package com.Back_end_AI.Back_end_AI.service;

import com.Back_end_AI.Back_end_AI.model.AppBook;
import com.Back_end_AI.Back_end_AI.model.AppUser;
import com.Back_end_AI.Back_end_AI.repo.AppBookRepository;
import com.Back_end_AI.Back_end_AI.repo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class AppBookService {

    @Autowired
    private AppBookRepository appBookRepository;

    public AppBook saveBook(AppBook book) {
        return appBookRepository.save(book);
    }

    public List<AppBook> getAllBooks() {
        List<AppBook> books = new ArrayList<>();
        appBookRepository.findAll().forEach(books::add);
        return books;
    }

}