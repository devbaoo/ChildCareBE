package com.example.demo.Service;

import com.example.demo.DTO.PostDTO;
import com.example.demo.Mapper.PostMapper;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.entity.Account;
import com.example.demo.entity.Category;
import com.example.demo.entity.Post;
import com.example.demo.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private  PostMapper postMapper;

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

    public List<PostDTO> getHotPosts() {
        return postRepository.findByFlagTrueAndStatusOrderByUpdatedAtDesc("ACTIVE")
                .stream()
                .map(postMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PostDTO> getLatestPosts() {
        return postRepository.findTop10ByOrderByUpdatedAtDesc()
                .stream()
                .map(postMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public PostDTO createPost(PostDTO postDTO) {
        // Kiểm tra tác giả có tồn tại không
        Optional<Account> authorOpt = accountRepository.findByUsername(postDTO.getAuthorName());
        if (authorOpt.isEmpty()) {
            throw new RuntimeException("Tác giả không tồn tại!");
        }

        // Kiểm tra danh mục có tồn tại không
        Optional<Category> categoryOpt = categoryRepository.findByTitle(postDTO.getCategoryTitle());
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Danh mục không tồn tại!");
        }
        // Tạo bài viết mới
        Post post = new Post();
        post.setThumbnail(postDTO.getThumbnail());
        post.setTitle(postDTO.getTitle());
        post.setBriefInformation(postDTO.getBriefInformation());
        post.setDescription(postDTO.getDescription());
        post.setAuthor(authorOpt.get());
        post.setCategory(categoryOpt.get());
        post.setUpdatedAt(LocalDateTime.now());
        post.setFlag(true);
        post.setStatus("ACTIVE");

        // Lưu bài viết
        Post savedPost = postRepository.save(post);

        // Chuyển đổi sang DTO để trả về
        return new PostDTO(
                savedPost.getId(),
                savedPost.getThumbnail(),
                savedPost.getTitle(),
                savedPost.getBriefInformation(),
                savedPost.getDescription(),
                savedPost.getAuthor().getUsername(),
                savedPost.getCategory().getTitle(),
                savedPost.getUpdatedAt()
        );
    }
}
