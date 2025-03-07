package com.example.demo.Service;

import com.example.demo.DTO.CategoryDTO;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        // Không cần setup đặc biệt vì @ExtendWith(MockitoExtension.class) đã lo việc mock
    }

    @Test
    void testGetAllCategories() {
        // Given: Dữ liệu giả lập với id là String
        Category category1 = new Category("1", "Category 1", null);
        Category category2 = new Category("2", "Category 2", null);

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // When: Gọi hàm cần test
        List<CategoryDTO> result = categoryService.getAllCategories();

        // Then: Kiểm tra kết quả
        assertEquals(2, result.size()); // Đúng số lượng phần tử
        assertEquals("Category 1", result.get(0).getTitle()); // Đúng dữ liệu
        assertEquals("Category 2", result.get(1).getTitle());
    }


    @Test
    void testGetAllCategories_EmptyList() {
        // Given: Repository trả về danh sách rỗng
        when(categoryRepository.findAll()).thenReturn(Arrays.asList());

        // When: Gọi hàm cần test
        List<CategoryDTO> result = categoryService.getAllCategories();

        // Then: Kiểm tra danh sách rỗng
        assertEquals(0, result.size());
    }
}
