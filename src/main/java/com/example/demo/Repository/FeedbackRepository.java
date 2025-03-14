package com.example.demo.Repository;

import com.example.demo.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    @Query("SELECT f FROM Feedback f " +
            "WHERE (:search IS NULL OR f.feedbackDetail LIKE %:search% " +
            "OR f.customer.id LIKE %:search%) " +
            "AND (:status IS NULL OR f.status = :status)")
    Page<Feedback> searchFeedbacks(@Param("search") String search,
                                   @Param("status") String status,
                                   Pageable pageable);
}
