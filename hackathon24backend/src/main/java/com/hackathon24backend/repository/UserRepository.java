package com.hackathon24backend.repository;

import com.hackathon24backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByMobileNumber(String mobileNum);
}
