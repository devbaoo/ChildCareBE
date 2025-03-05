package com.example.demo.Controller;

import com.example.demo.DTO.CreateServiceDTO;
import com.example.demo.DTO.ServiceDTO;
import com.example.demo.Service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/services")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    // 1️⃣ API lấy danh sách dịch vụ với phân trang
    @GetMapping
    public Page<ServiceDTO> getAllServices(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return serviceService.getAllServices(page, size);
    }

    // 2️⃣ API lấy chi tiết dịch vụ
    @GetMapping("/{id}")
    public Optional<ServiceDTO> getServiceById(@PathVariable String id) {
        return serviceService.getServiceById(id);
    }

    // 3️⃣ API thêm dịch vụ mới
    @PostMapping
    public ServiceDTO createService(@RequestBody CreateServiceDTO dto) {
        return serviceService.createService(dto);
    }

    // 4️⃣ API cập nhật dịch vụ
    @PutMapping("/{id}")
    public ServiceDTO updateService(@PathVariable String id, @RequestBody CreateServiceDTO dto) {
        return serviceService.updateService(id, dto);
    }

    // 5️⃣ API ẩn dịch vụ
    @PatchMapping("/{id}/hide")
    public void hideService(@PathVariable String id) {
        serviceService.hideService(id);
    }

    // 6️⃣ API hiển thị lại dịch vụ
    @PatchMapping("/{id}/show")
    public void showService(@PathVariable String id, @RequestParam int quantity) {
        serviceService.showService(id, quantity);
    }
}