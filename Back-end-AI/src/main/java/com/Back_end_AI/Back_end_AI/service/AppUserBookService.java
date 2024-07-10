package com.Back_end_AI.Back_end_AI.service;


import com.Back_end_AI.Back_end_AI.model.AppBook;
import com.Back_end_AI.Back_end_AI.model.AppUserBook;
import com.Back_end_AI.Back_end_AI.repo.AppUserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserBookService {

    @Autowired
    private AppUserBookRepository appUserBookRepository;

    public AppUserBook saveAppUserBook(AppUserBook appUserBook) {
        return appUserBookRepository.save(appUserBook);
    }

    public List<AppUserBook> getAllAppUserBooks() {
        List<AppUserBook> appUserBooks = new ArrayList<>();
        appUserBookRepository.findAll().forEach(appUserBooks::add);
        return appUserBooks;
    }
}
