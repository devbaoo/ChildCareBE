package com.example.demo.Repository;

import com.example.demo.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
    Page<ServiceEntity> findAll(Pageable pageable);
    Optional<ServiceEntity> findById(String id);
    List<ServiceEntity> findByServiceQuantityGreaterThanAndServicePriceGreaterThanOrderByServicePriceDesc(Integer quantity, BigDecimal price);
    @Query("SELECT s FROM ServiceEntity s WHERE LOWER(s.serviceName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ServiceEntity> searchByServiceName(@Param("name") String name);
}