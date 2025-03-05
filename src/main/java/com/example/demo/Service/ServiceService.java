package com.example.demo.Service;

import com.example.demo.DTO.CreateServiceDTO;
import com.example.demo.DTO.ServiceDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Service;
import com.example.demo.Mapper.ServiceMapper;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ServiceMapper serviceMapper;

    // 1️⃣ Lấy danh sách dịch vụ với phân trang
    public Page<ServiceDTO> getAllServices(int page, int size) {
        Page<Service> services = serviceRepository.findAll(PageRequest.of(page, size));
        return services.map(serviceMapper::toDTO);
    }

    // 2️⃣ Lấy chi tiết một dịch vụ
    public Optional<ServiceDTO> getServiceById(String id) {
        return serviceRepository.findById(id).map(serviceMapper::toDTO);
    }

    // 3️⃣ Thêm dịch vụ mới
    public ServiceDTO createService(CreateServiceDTO dto) {
        Optional<Category> categoryOpt = categoryRepository.findById(dto.getCategoryId());
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        Service service = new Service();
        service.setId(UUID.randomUUID().toString());
        service.setServiceName(dto.getServiceName());
        service.setServiceDetail(dto.getServiceDetail());
        service.setServiceQuantity(dto.getServiceQuantity());
        service.setServicePrice(dto.getServicePrice());
        service.setCategory(categoryOpt.get());

        serviceRepository.save(service);
        return serviceMapper.toDTO(service);
    }

    // 4️⃣ Cập nhật dịch vụ
    public ServiceDTO updateService(String id, CreateServiceDTO dto) {
        Optional<Service> serviceOpt = serviceRepository.findById(id);
        if (serviceOpt.isEmpty()) {
            throw new RuntimeException("Service not found");
        }

        Service service = serviceOpt.get();
        service.setServiceName(dto.getServiceName());
        service.setServiceDetail(dto.getServiceDetail());
        service.setServiceQuantity(dto.getServiceQuantity());
        service.setServicePrice(dto.getServicePrice());

        serviceRepository.save(service);
        return serviceMapper.toDTO(service);
    }

    // 5️⃣ Ẩn dịch vụ
    public void hideService(String id) {
        Optional<Service> serviceOpt = serviceRepository.findById(id);
        if (serviceOpt.isPresent()) {
            Service service = serviceOpt.get();
            service.setServiceQuantity(0); // Ẩn bằng cách set số lượng về 0
            serviceRepository.save(service);
        } else {
            throw new RuntimeException("Service not found");
        }
    }

    // 6️⃣ Hiển thị dịch vụ
    public void showService(String id, int quantity) {
        Optional<Service> serviceOpt = serviceRepository.findById(id);
        if (serviceOpt.isPresent()) {
            Service service = serviceOpt.get();
            service.setServiceQuantity(quantity); // Đặt số lượng mới khi hiển thị lại
            serviceRepository.save(service);
        } else {
            throw new RuntimeException("Service not found");
        }
    }
}