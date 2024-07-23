package com.kd.springboot_store.dao;

import com.kd.springboot_store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>
{

    User findByEmail(String email);



}
