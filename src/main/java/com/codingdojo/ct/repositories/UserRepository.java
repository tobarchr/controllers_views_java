package com.codingdojo.ct.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.ct.models.User;

public interface UserRepository extends CrudRepository <User, Long>{
    User findByEmail(String email);

}
