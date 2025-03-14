package com.example.demo.Repository;

import com.example.demo.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
    Optional<Account> findByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT a FROM Account a " +
            "WHERE a.role = 'USER' " +  // Giới hạn chỉ lấy tài khoản có role là USER
            "AND (:keyword IS NULL OR a.username LIKE %:keyword% OR a.email LIKE %:keyword% OR a.phoneNumber LIKE %:keyword%) " +
            "AND (:status IS NULL OR a.status = :status) " +
            "AND (:role IS NULL OR a.role = :role)")
    Page<Account> findAllWithFilters(
            @Param("keyword") String keyword,
            @Param("status") String status,
            @Param("role") String role,
            Pageable pageable);

}