package com.politicia.coreservice.repository;

import com.politicia.coreservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByName(String name);
}
