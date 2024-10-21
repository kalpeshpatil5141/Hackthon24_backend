package com.hackathon24backend.repository;

import com.hackathon24backend.entity.User;
import com.hackathon24backend.entity.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp,Long> {
    UserOtp findByUser(User user);
}
