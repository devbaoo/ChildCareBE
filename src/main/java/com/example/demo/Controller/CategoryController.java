package com.example.demo.Controller;

import com.example.demo.DTO.CategoryDTO;
import com.example.demo.Service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Category", description = "Quản lý danh mục sản phẩm")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Lấy danh sách danh mục", description = "API này lấy toàn bộ danh mục có trong hệ thống")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
