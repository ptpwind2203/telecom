package com.example.telecom_shop.controller;

import com.example.telecom_shop.dto.LoginResponseDTO;
import com.example.telecom_shop.dto.UserLoginDTO;
import com.example.telecom_shop.dto.UserRegisterDTO;
import com.example.telecom_shop.dto.UserResponseDTO;
import com.example.telecom_shop.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(@RequestBody UserRegisterDTO request) {
        try{
            userService.createUser(request);
            return ResponseEntity.ok("Thêm tài khoản thành công");
        }
        catch (BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserLoginDTO request) {
        LoginResponseDTO loginResponseDTO = userService.login(request);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {

        UserResponseDTO response = userService.getCurrentUser();

        return ResponseEntity.ok(response);
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        userService.logout();

        return ResponseEntity.ok(
                "Đăng xuất thành công!"
        );
    }
}
