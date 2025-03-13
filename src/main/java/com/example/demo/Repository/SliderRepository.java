package com.example.demo.Repository;

import com.example.demo.entity.Slider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SliderRepository extends JpaRepository<Slider, String> {
    @Query("SELECT s FROM Slider s WHERE " +
            "(:status IS NULL OR s.status = :status) " +
            "AND (:title IS NULL OR LOWER(s.title) LIKE LOWER(CONCAT('%', :title, '%')))")
    List<Slider> findByStatusAndTitle(@Param("status") String status, @Param("title") String title);
}