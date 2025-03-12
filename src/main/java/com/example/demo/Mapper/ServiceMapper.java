package com.example.demo.Mapper;

import com.example.demo.DTO.ServiceDTO;
import com.example.demo.entity.ServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {
    public ServiceDTO toDTO(ServiceEntity service) {
        return new ServiceDTO(
                service.getId(),
                service.getServiceName(),
                service.getServiceDetail(),
                service.getServiceQuantity(),
                service.getServicePrice(),
                service.getCategory() != null ? service.getCategory().getId() : null
        );
    }
}