package com.example.demo.Repository;

import com.example.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, String> {
    @Query("SELECT p FROM Post p ORDER BY p.updatedAt DESC")
    Page<Post> findAllByOrderByUpdatedAtDesc(Pageable pageable);


    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<Post> searchByTitle(String title, Pageable pageable);

    List<Post> findByCategoryId(String categoryId);

    Optional<Post> findById(String id);
}