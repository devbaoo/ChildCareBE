package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @Column(name = "ID", nullable = false, length = 255)
    private String id = UUID.randomUUID().toString();

    @Column(name = "Thumbnail", length = 255)
    private String thumbnail;

    @Column(name = "Title", length = 255)
    private String title;

    @Column(name = "BriefInformation", columnDefinition = "VARCHAR(255)")
    private String briefInformation;

    @Column(name = "Description", length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "Author", referencedColumnName = "ID")
    private Account author;

    @Column(name = "Flag")
    private Boolean flag;

    @Column(name = "Status", length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "CategoryID", referencedColumnName = "ID")
    private Category category;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}