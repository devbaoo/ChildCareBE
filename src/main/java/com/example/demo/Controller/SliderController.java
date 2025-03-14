package com.example.demo.Controller;

import com.example.demo.DTO.SliderDTO;
import com.example.demo.Service.SliderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sliders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SliderController {
    @Autowired
    private SliderService sliderService;

    @GetMapping
    public List<SliderDTO> getAllSliders() {
        return sliderService.getAllSliders();
    }

    // 1️⃣ API lấy danh sách slider (filter theo status, search theo title)
    @GetMapping("/list")
    public ResponseEntity<List<SliderDTO>> getSliders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String title) {
        List<SliderDTO> sliders = sliderService.getSliders(status, title);
        return ResponseEntity.ok(sliders);
    }

    // API lấy chi tiết Slider theo sliderId
    @GetMapping("/{sliderId}")
    public ResponseEntity<SliderDTO> getSliderDetail(@PathVariable String sliderId) {
        SliderDTO slider = sliderService.getSliderDetail(sliderId);
        return slider != null ? ResponseEntity.ok(slider) : ResponseEntity.notFound().build();
    }


    // 2️⃣ API Ẩn/Hiện slider
    @PutMapping("/{sliderId}/status")
    public ResponseEntity<String> toggleSliderStatus(
            @PathVariable String sliderId,
            @RequestParam String status) {
        boolean updated = sliderService.toggleSliderStatus(sliderId, status);
        return updated ? ResponseEntity.ok("Cập nhật trạng thái thành công")
                : ResponseEntity.badRequest().body("Không tìm thấy Slider");
    }

    // 3️⃣ API Chỉnh sửa slider
    @PutMapping("/{sliderId}")
    public ResponseEntity<String> updateSlider(
            @PathVariable String sliderId,
            @RequestBody SliderDTO sliderDTO) {
        sliderDTO.setSliderId(sliderId); // Đảm bảo đúng ID
        boolean updated = sliderService.updateSlider(sliderDTO);
        return updated ? ResponseEntity.ok("Cập nhật Slider thành công")
                : ResponseEntity.badRequest().body("Không tìm thấy Slider");
    }
}