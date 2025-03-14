package com.example.demo.Service;

import com.example.demo.DTO.AuthDTO;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final AccountRepository accountRepository;

    public Page<Account> getAllAccounts(int page, int size, String keyword, String status , String role) {
        return accountRepository.findAllWithFilters(keyword, status, role, PageRequest.of(page, size));
    }


    public Account getAccountById(int id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account createAccount(AuthDTO authDTO) {
        Account account = new Account();
        account.setUsername(authDTO.getUsername());
        account.setUserpass(authDTO.getUserpass());
        account.setRole("USER");
        account.setFirstname(authDTO.getFirstname());
        account.setLastname(authDTO.getLastname());
        account.setAge(authDTO.getAge());
        account.setEmail(authDTO.getEmail());
        account.setPhoneNumber(authDTO.getPhoneNumber());
        account.setStatus("ACTIVE");
        return accountRepository.save(account);
    }

    public Account updateAccount(int id, AuthDTO authDTO) {
        Account account = getAccountById(id);
        account.setFirstname(authDTO.getFirstname());
        account.setLastname(authDTO.getLastname());
        account.setAge(authDTO.getAge());
        account.setEmail(authDTO.getEmail());
        account.setPhoneNumber(authDTO.getPhoneNumber());
        account.setRole(authDTO.getRole());
        return accountRepository.save(account);
    }

    public void deleteAccount(int id) {
        Account account = getAccountById(id);
        account.setStatus("INACTIVE");
        accountRepository.save(account);
    }
}