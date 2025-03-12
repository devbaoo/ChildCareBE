package com.example.demo.Service;

import com.example.demo.DTO.CreateServiceDTO;
import com.example.demo.DTO.ServiceDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.ServiceEntity;
import com.example.demo.Mapper.ServiceMapper;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ServiceMapper serviceMapper;

    //  Lấy danh sách dịch vụ với phân trang
    public Page<ServiceDTO> getAllServices(int page, int size) {
        Page<ServiceEntity> services = serviceRepository.findAll(PageRequest.of(page, size));
        return services.map(serviceMapper::toDTO);
    }

    //  Lấy chi tiết một dịch vụ
    public Optional<ServiceDTO> getServiceById(String id) {
        return serviceRepository.findById(id).map(serviceMapper::toDTO);
    }

    //  Thêm dịch vụ mới
    public ServiceDTO createService(CreateServiceDTO dto) {
        Optional<Category> categoryOpt = categoryRepository.findById(dto.getCategoryId());
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        ServiceEntity service = new ServiceEntity();
        service.setId(UUID.randomUUID().toString());
        service.setServiceName(dto.getServiceName());
        service.setServiceDetail(dto.getServiceDetail());
        service.setServiceQuantity(dto.getServiceQuantity());
        service.setServicePrice(dto.getServicePrice());
        service.setCategory(categoryOpt.get());

        serviceRepository.save(service);
        return serviceMapper.toDTO(service);
    }

    //  Cập nhật dịch vụ
    public ServiceDTO updateService(String id, CreateServiceDTO dto) {
        Optional<ServiceEntity> serviceOpt = serviceRepository.findById(id);
        if (serviceOpt.isEmpty()) {
            throw new RuntimeException("Service not found");
        }

        ServiceEntity service = serviceOpt.get();
        service.setServiceName(dto.getServiceName());
        service.setServiceDetail(dto.getServiceDetail());
        service.setServiceQuantity(dto.getServiceQuantity());
        service.setServicePrice(dto.getServicePrice());

        serviceRepository.save(service);
        return serviceMapper.toDTO(service);
    }

    public List<ServiceDTO> getFeaturedServices() {
        return serviceRepository.findByServiceQuantityGreaterThanAndServicePriceGreaterThanOrderByServicePriceDesc(0, BigDecimal.ZERO)
                .stream()
                .map(serviceMapper::toDTO)
                .collect(Collectors.toList());
    }

    //  Ẩn dịch vụ
    public void hideService(String id) {
        Optional<ServiceEntity> serviceOpt = serviceRepository.findById(id);
        if (serviceOpt.isPresent()) {
            ServiceEntity service = serviceOpt.get();
            service.setServiceQuantity(0); // Ẩn bằng cách set số lượng về 0
            serviceRepository.save(service);
        } else {
            throw new RuntimeException("Service not found");
        }
    }

    //  Hiển thị dịch vụ
    public void showService(String id, int quantity) {
        Optional<ServiceEntity> serviceOpt = serviceRepository.findById(id);
        if (serviceOpt.isPresent()) {
            ServiceEntity service = serviceOpt.get();
            service.setServiceQuantity(quantity); // Đặt số lượng mới khi hiển thị lại
            serviceRepository.save(service);
        } else {
            throw new RuntimeException("Service not found");
        }
    }

    public List<ServiceDTO> searchServices(String name) {
        return serviceRepository.searchByServiceName(name)
                .stream()
                .map(service -> new ServiceDTO(
                        service.getId(),
                        service.getServiceName(),
                        service.getServiceDetail(),
                        service.getServiceQuantity(),
                        service.getServicePrice(),
                        service.getCategory().getId()
                ))
                .collect(Collectors.toList());
    }

}