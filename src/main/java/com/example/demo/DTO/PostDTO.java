package com.example.demo.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private String id;
    private String thumbnail;
    private String title;
    private String briefInformation;
    private String description;
    private String authorName;
    private String categoryTitle;
    private LocalDateTime updatedAt;
}
