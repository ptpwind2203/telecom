package com.example.telecom_shop.service;

import com.example.telecom_shop.dto.userDTO.*;
import com.example.telecom_shop.enums.AccountRole;
import com.example.telecom_shop.enums.UserStatus;
import com.example.telecom_shop.models.User;
import com.example.telecom_shop.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;
    @Autowired private SecurityContextRepository securityContextRepository;

    // =========================================================
    // CREATE ACCOUNT
    // =========================================================
    public void createUser(UserRegisterDTO req) throws BadRequestException {
        validateBlank(req.getFull_name(), "Họ và tên không được để trống!");
        validateBlank(req.getEmail(), "Email không được để trống!");
        validateBlank(req.getPhone(), "Số điện thoại không được để trống!");
        validateBlank(req.getPassword(), "Mật khẩu không được để trống!");

        if (userRepository.existsByEmail(req.getEmail())) throw new BadRequestException("Email đã tồn tại!");
        if (userRepository.existsByPhone(req.getPhone())) throw new BadRequestException("Số điện thoại đã tồn tại!");

        User user = new User();
        user.setFull_name(req.getFull_name());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPassword_hash(passwordEncoder.encode(req.getPassword()));
        user.setRole(AccountRole.CUSTOMER);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreated_at(LocalDate.now());
        user.setUpdate_at(LocalDate.now());

        userRepository.save(user);
    }

    // =========================================================
    // LOGIN
    // =========================================================
    public LoginResponseDTO login(UserLoginDTO req, HttpServletRequest request, HttpServletResponse response) {
        validateBlank(req.getPassword(), "Mật khẩu không được để trống!");
        validateBlank(req.getAccount(), "Email hoặc số điện thoại đang trống!");

        User user = userRepository.findByPhoneOrEmail(req.getAccount(), req.getAccount())
                .orElseThrow(() -> new RuntimeException("Email hoặc số điện thoại không tồn tại"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword_hash())) {
            throw new RuntimeException("Mật khẩu không chính xác");
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException("Tài khoản đã bị khóa hoặc không hoạt động");
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getId(), null, List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        LoginResponseDTO res = new LoginResponseDTO();
        res.setUser(convertToResponseDTO(user));
        return res;
    }

    // =========================================================
    // GET CURRENT USER
    // =========================================================
    public UserResponseDTO getCurrentUser() {
        return convertToResponseDTO(findAuthenticatedUser());
    }

    // =========================================================
    // LOGOUT
    // =========================================================
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
    }

    // =========================================================
    // UPDATE ACCOUNT
    // =========================================================
    public UserResponseDTO updateUserDTO(UserUpdateDTO req) {
        User user = findAuthenticatedUser();

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword_hash())) {
            throw new RuntimeException("Mật khẩu hiện tại không chính xác");
        }

        user.setFull_name(req.getFull_name());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUpdate_at(LocalDate.now());

        return convertToResponseDTO(userRepository.save(user));
    }

    // =========================================================
    // UPDATE PASSWORD
    // =========================================================
    public void UserUpdatePassword(UserPasswordDTO req) {
        User user = findAuthenticatedUser();

        if (!passwordEncoder.matches(req.getPassword_old(), user.getPassword_hash())) {
            throw new RuntimeException("Mật khẩu cũ không chính xác");
        }
        validateBlank(req.getPassword_new(), "Mật khẩu mới không được để trống");
        if (!req.getPassword_new().equals(req.getComfirm_password())) {
            throw new RuntimeException("Mật khẩu xác nhận không trùng khớp");
        }

        user.setPassword_hash(passwordEncoder.encode(req.getPassword_new()));
        user.setUpdate_at(LocalDate.now());
        userRepository.save(user);
    }

    // =========================================================
    // HELPER METHODS
    // =========================================================
    private User findAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof Integer)) {
            throw new RuntimeException("Bạn chưa đăng nhập!");
        }
        return userRepository.findById((Integer) auth.getPrincipal())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản!"));
    }

    private void validateBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new RuntimeException(message); // Hoặc giữ nguyên BadRequestException tùy theo yêu cầu
        }
    }

    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO res = new UserResponseDTO();
        res.setId(user.getId());
        res.setFull_name(user.getFull_name());
        res.setEmail(user.getEmail());
        res.setPhone(user.getPhone());
        res.setRole(user.getRole());
        res.setStatus(user.getStatus());
        res.setCreated_at(user.getCreated_at());
        res.setUpdate_at(user.getUpdate_at());
        return res;
    }
}