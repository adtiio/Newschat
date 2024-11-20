package com.newsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    User findByUsername(String username);

    List<User> findByUsernameStartingWith(String username);
}
