package com.example.demo.Service;

import com.example.demo.DTO.*;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.entity.Account;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Authentication.config.JwtTokenProvider;
import com.example.demo.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;
    private final CustomerRepository customerRepository;

    // Đăng ký tài khoản mới
    public String register(RegisterDTO registerDTO) {
        if (accountRepository.existsByEmail(registerDTO.getEmail())) {
            return "Email đã tồn tại!";
        }

        // 1️⃣ Tạo tài khoản trong bảng Account
        Account account = new Account();
        account.setUsername(registerDTO.getUsername());
        account.setUserpass(passwordEncoder.encode(registerDTO.getUserpass()));
        account.setFirstname(registerDTO.getFirstname());
        account.setLastname(registerDTO.getLastname());
        account.setAge(registerDTO.getAge());
        account.setEmail(registerDTO.getEmail());
        account.setPhoneNumber(registerDTO.getPhoneNumber());
        account.setAddress(registerDTO.getAddress());
        account.setRole("USER");  // Mặc định role là USER
        account.setStatus("INACTIVE");
        account.setEmailVerified(false);

        // ✅ Lưu tài khoản vào database
        Account savedAccount = accountRepository.save(account);

        // 2️⃣ Nếu role là "USER", tạo bản ghi trong bảng Customer
        if ("USER".equals(savedAccount.getRole())) {
            Customer customer = new Customer();
            customer.setId(UUID.randomUUID().toString()); // Sinh ID ngẫu nhiên
            customer.setAccount(savedAccount);

            customerRepository.save(customer); // ✅ Lưu Customer vào database
        }

        // 3️⃣ Gửi email xác thực
        String subject = "Xác thực tài khoản ChildCare";
        String content = "Chào " + account.getFirstname() + ",<br><br>"
                + "Cảm ơn bạn đã đăng ký tài khoản. Vui lòng nhấn vào link dưới đây để xác thực tài khoản:<br>"
                + "<a href='http://localhost:8080/api/auth/verify?email=" + account.getEmail() + "'>Xác thực ngay</a>"
                + "<br><br>Trân trọng, <br>ChildCare Team";

        try {
            emailService.sendEmail(account.getEmail(), subject, content);
        } catch (Exception e) {
            return "Đăng ký thành công nhưng lỗi gửi email: " + e.getMessage();
        }

        return "Đăng ký thành công! Vui lòng kiểm tra email để xác thực tài khoản.";
    }


    // Đăng nhập
    public AuthResponseDTO login(LoginDTO loginDTO) {
        Optional<Account> accountOpt = accountRepository.findByEmail(loginDTO.getEmail());
        if (accountOpt.isEmpty() || !passwordEncoder.matches(loginDTO.getUserpass(), accountOpt.get().getUserpass())) {
            return null; // Sai email hoặc mật khẩu
        }

        Account account = accountOpt.get();
        if (!account.isEmailVerified()) {
            return null; // Chưa xác thực email
        }

        String token = jwtTokenProvider.generateToken(account.getEmail(), account.getRole());

        // Lưu token vào database
        account.setJwtToken(token);
        accountRepository.save(account);

        return new AuthResponseDTO(token, account.getRole());
    }


    // Gửi email đặt lại mật khẩu
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        Optional<Account> accountOpt = accountRepository.findByEmail(resetPasswordDTO.getEmail());
        if (accountOpt.isEmpty()) {
            return "Email không tồn tại!";
        }

        Account account = accountOpt.get();
        String resetToken = jwtTokenProvider.generateResetToken(account.getEmail());
        account.setResetToken(resetToken);
        accountRepository.save(account);

        // Gửi email
        String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;
        emailService.sendEmail(account.getEmail(), "Đặt lại mật khẩu", "Nhấp vào link để đặt lại mật khẩu: " + resetLink);

        return "Vui lòng kiểm tra email để đặt lại mật khẩu.";
    }

    // Đặt lại mật khẩu
    public String confirmResetPassword(String token, String newPassword) {
        if (!jwtTokenProvider.validateToken(token)) {
            return "Token không hợp lệ hoặc đã hết hạn!";
        }

        String email = jwtTokenProvider.getEmailFromToken(token); // Sửa lại lỗi gọi hàm tĩnh sai
        Optional<Account> accountOpt = accountRepository.findByEmail(email);
        if (accountOpt.isEmpty()) {
            return "Tài khoản không tồn tại!";
        }

        Account account = accountOpt.get();

        // Mã hóa mật khẩu trước khi lưu
        String hashedPassword = passwordEncoder.encode(newPassword);
        account.setUserpass(hashedPassword); // Sử dụng setUserpass()

        account.setResetToken(null); // Xóa reset token sau khi cập nhật mật khẩu
        accountRepository.save(account);

        return "Mật khẩu đã được cập nhật thành công!";
    }

    public String verifyEmail(String email) {
        Optional<Account> accountOpt = accountRepository.findByEmail(email);
        if (accountOpt.isEmpty()) {
            return "Tài khoản không tồn tại!";
        }

        Account account = accountOpt.get();
        if (account.isEmailVerified()) {
            return "Tài khoản đã được xác thực trước đó!";
        }

        // ✅ Cập nhật trạng thái tài khoản
        account.setEmailVerified(true);
        account.setStatus("ACTIVE");
        accountRepository.save(account);

        return "Xác thực tài khoản thành công! Bạn có thể đăng nhập ngay bây giờ.";
    }



    // Đổi mật khẩu
    public String changePassword(ChangePasswordDTO changePasswordDTO, String email) {
        Optional<Account> accountOpt = accountRepository.findByEmail(email);
        if (accountOpt.isEmpty()) {
            return "Tài khoản không tồn tại!";
        }

        Account account = accountOpt.get();
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), account.getUserpass())) {
            return "Mật khẩu cũ không đúng!";
        }

        account.setUserpass(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        accountRepository.save(account);
        return "Đổi mật khẩu thành công!";
    }
}