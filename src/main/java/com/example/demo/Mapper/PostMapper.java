package com.example.demo.Mapper;

import com.example.demo.DTO.CategoryDTO;
import com.example.demo.DTO.PostDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBriefInformation(post.getBriefInformation());
        dto.setDescription(post.getDescription());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setThumbnail(post.getThumbnail());
        dto.setUpdatedAt(post.getUpdatedAt());

        // Set authorName instead of authorUsername
        if (post.getAuthor() != null && post.getCategory() != null) {
            dto.setAuthorName(post.getAuthor().getUsername());
            dto.setCategoryTitle(post.getCategory().getTitle());
        } else {
            dto.setAuthorName("Unknown"); // Default value
            dto.setCategoryTitle("Uncategorized");
        }

        return dto;
    }
}
