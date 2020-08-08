package com.testtask.currencyconverter.repositories;

import com.testtask.currencyconverter.entities.auth.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
