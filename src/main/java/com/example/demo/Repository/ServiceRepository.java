package com.example.demo.Repository;

import com.example.demo.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, String> {
    Page<Service> findAll(Pageable pageable);
    Optional<Service> findById(String id);
}