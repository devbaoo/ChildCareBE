package com.example.demo.Service;

import com.example.demo.DTO.PostDTO;
import com.example.demo.Mapper.PostMapper;
import com.example.demo.Repository.PostRepository;
import com.example.demo.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    private Post post1, post2;
    private PostDTO postDTO1, postDTO2;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        post1 = new Post();
        post1.setId("1");
        post1.setTitle("Post Title 1");
        post1.setBriefInformation("Brief 1");
        post1.setDescription("Description 1");
        post1.setUpdatedAt(LocalDateTime.now());

        post2 = new Post();
        post2.setId("2");
        post2.setTitle("Post Title 2");
        post2.setBriefInformation("Brief 2");
        post2.setDescription("Description 2");
        post2.setUpdatedAt(LocalDateTime.now());

        postDTO1 = new PostDTO("1", "thumb1.jpg", "Post Title 1", "Brief 1", "Description 1", "Author1", "Category1", LocalDateTime.now());
        postDTO2 = new PostDTO("2", "thumb2.jpg", "Post Title 2", "Brief 2", "Description 2", "Author2", "Category2", LocalDateTime.now());

        pageable = PageRequest.of(0, 10, Sort.by("updatedAt").descending());
    }

    // ✅ Test getPaginatedPosts()
    @Test
    void testGetPaginatedPosts() {
        Page<Post> postPage = new PageImpl<>(Arrays.asList(post1, post2));
        when(postRepository.findAllByOrderByUpdatedAtDesc(pageable)).thenReturn(postPage);
        when(postMapper.convertToDTO(post1)).thenReturn(postDTO1);
        when(postMapper.convertToDTO(post2)).thenReturn(postDTO2);

        Page<PostDTO> result = postService.getPaginatedPosts(pageable);

        assertEquals(2, result.getContent().size());
        assertEquals("Post Title 1", result.getContent().get(0).getTitle());
        assertEquals("Post Title 2", result.getContent().get(1).getTitle());
    }

    // ✅ Test searchPosts()
    @Test
    void testSearchPosts() {
        Page<Post> postPage = new PageImpl<>(Arrays.asList(post1));
        when(postRepository.searchByTitle("Post Title 1", pageable)).thenReturn(postPage);
        when(postMapper.convertToDTO(post1)).thenReturn(postDTO1);

        Page<PostDTO> result = postService.searchPosts("Post Title 1", pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Post Title 1", result.getContent().get(0).getTitle());
    }

    // ✅ Test getPostsByCategory()
    @Test
    void testGetPostsByCategory() {
        List<Post> postList = Arrays.asList(post1, post2);
        when(postRepository.findByCategoryId("category1")).thenReturn(postList);
        when(postMapper.convertToDTO(post1)).thenReturn(postDTO1);
        when(postMapper.convertToDTO(post2)).thenReturn(postDTO2);

        List<PostDTO> result = postService.getPostsByCategory("category1");

        assertEquals(2, result.size());
        assertEquals("Post Title 1", result.get(0).getTitle());
        assertEquals("Post Title 2", result.get(1).getTitle());
    }

    // ✅ Test getPostById() khi bài viết tồn tại
    @Test
    void testGetPostById_Found() {
        when(postRepository.findById("1")).thenReturn(Optional.of(post1));
        when(postMapper.convertToDTO(post1)).thenReturn(postDTO1);

        PostDTO result = postService.getPostById("1");

        assertNotNull(result);
        assertEquals("Post Title 1", result.getTitle());
    }

    // ✅ Test getPostById() khi bài viết không tồn tại
    @Test
    void testGetPostById_NotFound() {
        when(postRepository.findById("99")).thenReturn(Optional.empty());

        PostDTO result = postService.getPostById("99");

        assertNull(result);
    }
}
