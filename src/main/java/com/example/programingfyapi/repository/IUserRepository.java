package com.example.programingfyapi.repository;

import com.example.programingfyapi.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {

}
