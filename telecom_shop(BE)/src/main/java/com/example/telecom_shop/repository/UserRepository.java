package com.example.telecom_shop.repository;

import com.example.telecom_shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {

    //Dùng để kiểm tra sự tồn tại của phone trong DB
    boolean existsByPhone(String phone);
    //Dùng để kiểm tra sự tồn tại của email trong DB
    boolean existsByEmail(String email);

    Optional<User> findByPhoneOrEmail(String phone, String email);

}
