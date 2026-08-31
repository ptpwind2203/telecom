package com.example.telecom_shop.service;


import com.example.telecom_shop.dto.userDTO.*;
import com.example.telecom_shop.enums.AccountRole;
import com.example.telecom_shop.enums.UserStatus;
import com.example.telecom_shop.models.User;
import com.example.telecom_shop.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession session;

    public void createUser(UserRegisterDTO request) throws BadRequestException {
        if (request.getFull_name() == null ||  request.getFull_name().isBlank()) {
            throw new BadRequestException("Họ và tên không được để trống!");
        }
        if (request.getEmail() == null ||  request.getEmail().isBlank()) {
            throw new BadRequestException("Email không được để trống!");
        }
        if (request.getPhone() == null ||  request.getPhone().isBlank()) {
            throw new BadRequestException("Số điện thoại không được để trống!");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BadRequestException("Mật khẩu không được để trống!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email đã tồn tại!");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Số điện thoại đã tồn tại!");
        }

        User user = new User();
        user.setFull_name(request.getFull_name());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword_hash(passwordEncoder.encode(request.getPassword()));
        user.setRole(AccountRole.CUSTOMER);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreated_at(LocalDate.now());
        user.setUpdate_at(LocalDate.now());

        userRepository.save(user);
    }

    public LoginResponseDTO login(UserLoginDTO request) {

        if (request.getPassword() == null ||
                request.getPassword().isBlank()) {

            throw new RuntimeException(
                    "Mật khẩu không được để trống!"
            );
        }

        if (request.getAccount() == null ||
                request.getAccount().isBlank()) {

            throw new RuntimeException(
                    "Email hoặc số điện thoại đang trống!"
            );
        }

        // Tìm user bằng email hoặc số điện thoại
        User user = userRepository
                .findByPhoneOrEmail(
                        request.getAccount(),
                        request.getAccount()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Email hoặc số điện thoại không tồn tại"
                        )
                );

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword_hash()
        )) {
            throw new RuntimeException(
                    "Mật khẩu không chính xác"
            );
        }

        // Kiểm tra trạng thái tài khoản
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException(
                    "Tài khoản đã bị khóa hoặc không hoạt động"
            );
        }

        // ==========================================
        // LƯU USER ID VÀO SESSION
        // ==========================================

        session.setAttribute("userId", user.getId());

        // ==========================================
        // CHUYỂN USER -> USER RESPONSE DTO
        // ==========================================

        UserResponseDTO userResponse = new UserResponseDTO();

        userResponse.setId(user.getId());
        userResponse.setFull_name(user.getFull_name());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());
        userResponse.setStatus(user.getStatus());
        userResponse.setCreated_at(user.getCreated_at());
        userResponse.setUpdate_at(user.getUpdate_at());

        // ==========================================
        // TẠO LOGIN RESPONSE
        // ==========================================

        LoginResponseDTO response = new LoginResponseDTO();

        response.setUser(userResponse);

        return response;
    }

    public UserResponseDTO getCurrentUser() {

        // Lấy userId từ session
        Integer userId = (Integer) session.getAttribute("userId");

        // Chưa đăng nhập
        if (userId == null) {
            throw new RuntimeException("Bạn chưa đăng nhập!");
        }

        // Tìm user trong database
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy tài khoản!")
                );

        // Chuyển User Entity -> UserResponseDTO
        UserResponseDTO response = new UserResponseDTO();

        response.setId(user.getId());
        response.setFull_name(user.getFull_name());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        response.setCreated_at(user.getCreated_at());
        response.setUpdate_at(user.getUpdate_at());

        return response;
    }

    public void logout() {

        // Kiểm tra user có đang đăng nhập không
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("Bạn chưa đăng nhập!");
        }

        // Hủy session
        session.invalidate();
    }

    public UserResponseDTO updateUserDTO(UserUpdateDTO request) {
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword_hash())) {
            throw new RuntimeException("Mật khẩu hiện tại không chính xác");
        }

        user.setFull_name(request.getFull_name());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setUpdate_at(LocalDate.now());
        User updateUser = userRepository.save(user);
        return convertToResponseDTO(updateUser);

    }
    private UserResponseDTO convertToResponseDTO(User user) {

        UserResponseDTO response = new UserResponseDTO();

        response.setId(user.getId());
        response.setFull_name(user.getFull_name());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        response.setCreated_at(user.getCreated_at());
        response.setUpdate_at(user.getUpdate_at());

        return response;
    }

    public void UserUpdatePassword(UserPasswordDTO request) {
        Integer userId = (Integer) session.getAttribute("userId");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        if (!passwordEncoder.matches(request.getPassword_old(), user.getPassword_hash())) {
            throw new RuntimeException("Mật khẩu cũ không chính xác");
        }
        if(!request.getPassword_new().equals(request.getComfirm_password())) {
            throw new RuntimeException("Mật khẩu xác nhận không trùng khớp");
        }

        user.setPassword_hash(passwordEncoder.encode(request.getPassword_new()));

        userRepository.save(user);

    }
}
