package com.example.Shop.repo;

import com.example.Shop.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByLogin(String login);

}
