package com.example.demo.Service;

import com.example.demo.DTO.PostDTO;
import com.example.demo.Mapper.PostMapper;
import com.example.demo.entity.Post;
import com.example.demo.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private  PostMapper postMapper; // ✅ Inject PostMapper

    public Page<PostDTO> getPaginatedPosts(Pageable pageable) {

        Page<Post> posts = postRepository.findAllByOrderByUpdatedAtDesc(pageable);


        return posts.map(postMapper::convertToDTO);
    }

    public Page<PostDTO> searchPosts(String title, Pageable pageable) {
        return postRepository.searchByTitle(title, pageable).map(postMapper::convertToDTO);
    }

    public List<PostDTO> getPostsByCategory(String categoryId) {
        return postRepository.findByCategoryId(categoryId).stream()
                .map(postMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(postMapper::convertToDTO).orElse(null);
    }
}
