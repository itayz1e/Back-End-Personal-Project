package com.Back_end_AI.Back_end_AI.service;


import com.Back_end_AI.Back_end_AI.model.AppUser;
import com.Back_end_AI.Back_end_AI.repo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public AppUser saveUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public List<AppUser> getAllUsers() {
        List<AppUser> users = new ArrayList<>();
        appUserRepository.findAll().forEach(users::add);
        return users;
    }

    public List<Object[]> executeCustomQuery(String sql) {
        return entityManager.createNativeQuery(sql).getResultList();
            }
}