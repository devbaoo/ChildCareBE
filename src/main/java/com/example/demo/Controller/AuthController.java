package com.example.demo.Controller;


import com.example.demo.Authentication.config.JwtTokenProvider;
import com.example.demo.DTO.*;
import com.example.demo.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;

    // Đăng ký tài khoản
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        String response = authService.register(registerDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String email) {
        String response = authService.verifyEmail(email);
        return ResponseEntity.ok(response);
    }

    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        AuthResponseDTO response = authService.login(loginDTO);
        if (response == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(response);
    }

    // Gửi email đặt lại mật khẩu
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        String response = authService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok(response);
    }

    // Xác nhận đặt lại mật khẩu
    @PostMapping("/confirm-reset-password")
    public ResponseEntity<String> confirmResetPassword(@RequestParam String token, @RequestParam String newPassword) {
        String response = authService.confirmResetPassword(token, newPassword);
        return ResponseEntity.ok(response);
    }

    // Đổi mật khẩu
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO,
                                                 @RequestHeader("Authorization") String token) {
        String email = JwtTokenProvider.getEmailFromToken(token.substring(7)); // Loại bỏ "Bearer "
        String response = authService.changePassword(changePasswordDTO, email);
        return ResponseEntity.ok(response);
    }
}