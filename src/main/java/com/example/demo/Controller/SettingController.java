package com.example.demo.Controller;

import com.example.demo.DTO.SettingDTO;
import com.example.demo.Service.SettingService;
import com.example.demo.entity.SettingStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Setting Management", description = "APIs for managing settings")
@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SettingController {

    private final SettingService settingService;

    @Operation(summary = "Lấy danh sách Setting", description = "API hỗ trợ lọc, tìm kiếm, phân trang và sắp xếp")
    @GetMapping
    public ResponseEntity<Page<SettingDTO>> getSettings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) SettingStatus status,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(settingService.getSettings(page, size, status, keyword, sortBy, sortDirection));
    }

    @Operation(summary = "Lấy chi tiết Setting", description = "API hiển thị chi tiết Setting theo id")
    @GetMapping("/{id}")
    public ResponseEntity<SettingDTO> getSettingByKey(@PathVariable String id) {
        return ResponseEntity.ok(settingService.getSettingByKey(id));
    }

    @Operation(summary = "Thêm mới Setting", description = "API cho phép admin thêm Setting mới")
    @PostMapping
    public ResponseEntity<SettingDTO> createSetting(@RequestBody @Valid SettingDTO settingDTO) {
        return ResponseEntity.ok(settingService.createSetting(settingDTO));
    }

    @Operation(summary = "Cập nhật Setting", description = "API cho phép admin chỉnh sửa thông tin Setting")
    @PutMapping("/{id}")
    public ResponseEntity<SettingDTO> updateSetting(@PathVariable String id, @RequestBody @Valid SettingDTO settingDTO) {
        return ResponseEntity.ok(settingService.updateSetting(id, settingDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteSetting(@PathVariable String id) {
        settingService.softDeleteSetting(id);
        return ResponseEntity.ok().build();
    }



}
