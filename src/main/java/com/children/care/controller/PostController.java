//package com.children.care.controller;
//
//import com.children.care.entity.Post;
//import com.children.care.service.PostService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/posts")
//class PostController {
//    @Autowired
//    private PostService postService;
//
//    @GetMapping
//    public ResponseEntity<List<Post>> getAllPosts() {
//        return ResponseEntity.ok(postService.getAllPosts());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
//        Post post = postService.getPostById(id);
//        return (post != null) ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
//    }
//
//    @PostMapping
//    public ResponseEntity<Post> createPost(@RequestBody Post post) {
//        return ResponseEntity.ok(postService.createPost(post));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
//        Post updatedPost = postService.updatePost(id, post);
//        return (updatedPost != null) ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
//        return postService.deletePost(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
//    }
//}