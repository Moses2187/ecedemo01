package com.dev.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.demo.domain.User;
@Repository

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsernameAndPassword(String username,String password);

}
