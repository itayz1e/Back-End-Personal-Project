package com.Back_end_AI.Back_end_AI.repo;


import com.Back_end_AI.Back_end_AI.model.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser,Long> {

}