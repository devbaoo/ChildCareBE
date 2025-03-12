package com.example.demo.Authentication.service;

import com.example.demo.entity.Account;
import com.example.demo.Repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service  // Đánh dấu Spring Bean
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> accountOpt = accountRepository.findByEmail(email);
        if (accountOpt.isEmpty()) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email);
        }

        Account account = accountOpt.get();
        return User.withUsername(account.getEmail())
                .password(account.getUserpass()) // Mật khẩu đã mã hóa
                .roles(account.getRole()) // Định dạng ROLE_USER, ROLE_ADMIN
                .build();
    }
}
