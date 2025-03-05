package com.example.demo.Controller;

import com.example.demo.DTO.PostDTO;
import com.example.demo.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public Page<PostDTO> getPosts(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        System.out.println("➡️ API /posts được gọi với page=" + page + " size=" + size);

        Page<PostDTO> posts = postService.getPaginatedPosts(PageRequest.of(page, size));

        System.out.println("➡️ API /posts trả về " + posts.getTotalElements() + " bài viết.");
        return posts;
    }



    @GetMapping("/search")
    public Page<PostDTO> searchPosts(@RequestParam String title,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return postService.searchPosts(title, PageRequest.of(page, size));
    }

    @GetMapping("/category/{categoryId}")
    public List<PostDTO> getPostsByCategory(@PathVariable String categoryId) {
        return postService.getPostsByCategory(categoryId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable String id) {
        PostDTO post = postService.getPostById(id);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }
}
