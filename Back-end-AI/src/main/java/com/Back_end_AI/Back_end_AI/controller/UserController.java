package com.Back_end_AI.Back_end_AI.controller;

import com.Back_end_AI.Back_end_AI.model.AppUser;
import com.Back_end_AI.Back_end_AI.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AppUserService appUserService;

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user) {
        return appUserService.saveUser(user);
    }

    @GetMapping
    public List<AppUser> getAllUsers() {
        return appUserService.getAllUsers();
    }
}
