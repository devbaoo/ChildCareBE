package com.example.demo.Service;

import com.example.demo.DTO.AuthDTO;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.ManagerRepository;
import com.example.demo.Repository.StaffRepository;
import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Manager;
import com.example.demo.entity.Staff;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final StaffRepository staffRepository;
    private final ManagerRepository managerRepository;

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

        // 1️⃣ Xóa bản ghi cũ nếu tài khoản đổi role
        customerRepository.deleteByAccountId(account.getId());
        staffRepository.deleteByAccountId(account.getId());
        managerRepository.deleteByAccountId(account.getId());

        // 2️⃣ Cập nhật thông tin tài khoản
        account.setFirstname(authDTO.getFirstname());
        account.setLastname(authDTO.getLastname());
        account.setAge(authDTO.getAge());
        account.setEmail(authDTO.getEmail());
        account.setPhoneNumber(authDTO.getPhoneNumber());
        account.setRole(authDTO.getRole());

        // 3️⃣ Thêm bản ghi mới vào bảng tương ứng
        switch (authDTO.getRole()) {
            case "USER":
                Customer customer = new Customer();
                customer.setId(UUID.randomUUID().toString());
                customer.setAccount(account);
                customerRepository.save(customer);
                break;

            case "STAFF":
                Staff staff = new Staff();
                staff.setId(UUID.randomUUID().toString());
                staff.setAccount(account);
                staff.setSpecialization(authDTO.getSpecialization()); // Nếu cần chuyên môn
                staffRepository.save(staff);
                break;

            case "MANAGER":
                Manager manager = new Manager();
                manager.setId(UUID.randomUUID().toString());
                manager.setAccount(account);
                managerRepository.save(manager);
                break;
        }

        return accountRepository.save(account);
    }


    public void deleteAccount(int id) {
        Account account = getAccountById(id);
        account.setStatus("INACTIVE");
        accountRepository.save(account);
    }
}