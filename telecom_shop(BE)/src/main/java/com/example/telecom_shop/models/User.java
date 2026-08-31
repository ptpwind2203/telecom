package com.example.telecom_shop.models;

import com.example.telecom_shop.enums.AccountRole;
import com.example.telecom_shop.enums.UserStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "full_name")
    private String full_name;
    @Column(unique = true, nullable = false, name = "email")
    private String email;
    @Column(unique = true, nullable = false, name = "phone")
    private String phone;
    @Column(nullable = false, name = "password_hash")
    private String password_hash;

    @Column(name = "created_at")
    private LocalDate created_at;
    @Column(name = "update_at")
    private LocalDate update_at;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private AccountRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;
    public LocalDate getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDate update_at) {
        this.update_at = update_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
}
