package com.example.demo.Repository;

import com.example.demo.entity.Setting;
import com.example.demo.entity.SettingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {

    @Query("SELECT s FROM Setting s " +
            "WHERE (:status IS NULL OR s.status = :status) " +
            "AND (:keyword IS NULL OR s.settingValue LIKE CONCAT('%', :keyword, '%'))")
    Page<Setting> findAllWithFilters(
            @Param("status") SettingStatus status,
            @Param("keyword") String keyword,
            Pageable pageable);
}
