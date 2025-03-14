package com.example.demo.Controller;

import com.example.demo.DTO.AuthDTO;
import com.example.demo.Service.CustomerService;
import com.example.demo.entity.Account;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {
    private final CustomerService customerService;

    // 3.1. Lấy danh sách khách hàng (phân trang + filter + search)
    @Operation(summary = "Lấy danh sách tài khoản", description = "API hỗ trợ tìm kiếm theo keyword, status, gender, role")
    @GetMapping
    public ResponseEntity<Page<Account>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String role) {
        return ResponseEntity.ok(customerService.getAllAccounts(page, size, keyword, status, role));
    }


    // 3.2. Xem chi tiết thông tin khách hàng
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable int id) {
        return ResponseEntity.ok(customerService.getAccountById(id));
    }

    // 3.3. Thêm mới khách hàng
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(customerService.createAccount(authDTO));
    }

    // 3.4. Chỉnh sửa thông tin khách hàng
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable int id, @RequestBody AuthDTO authDTO) {
        try {
            Account updatedAccount = customerService.updateAccount(id, authDTO);
            return ResponseEntity.ok(updatedAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi cập nhật tài khoản: " + e.getMessage());
        }
    }


    // 3.5. Xóa khách hàng (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int id) {
        customerService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}