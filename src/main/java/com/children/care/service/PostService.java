//package com.children.care.service;
//
//import com.children.care.entity.Post;
//import com.children.care.repository.PostRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class PostService {
//    @Autowired
//    private PostRepository postRepository;
//
//    public List<Post> getAllPosts() {
//        return postRepository.findAll();
//    }
//
//    public Post getPostById(Long id) {
//        return postRepository.findById(id).orElse(null);
//    }
//
//    public Post createPost(Post post) {
//        return postRepository.save(post);
//    }
//
//    public Post updatePost(Long id, Post updatedPost) {
//        return postRepository.findById(id).map(post -> {
//            post.setThumbnail(updatedPost.getThumbnail());
//            post.setTitle(updatedPost.getTitle());
//            post.setBriefInformation(updatedPost.getBriefInformation());
//            post.setDescription(updatedPost.getDescription());
//            post.setAuthor(updatedPost.getAuthor());
//            post.setFlag(updatedPost.getFlag());
//            post.setStatus(updatedPost.getStatus());
//            post.setCategoryId(updatedPost.getCategoryId());
//            return postRepository.save(post);
//        }).orElse(null);
//    }
//
//    public boolean deletePost(Long id) {
//        if (postRepository.existsById(id)) {
//            postRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }
//}
