package com.example.demo.Mapper;

import com.example.demo.DTO.ServiceDTO;
import com.example.demo.entity.Service;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {
    public ServiceDTO toDTO(Service service) {
        return new ServiceDTO(
                service.getId(),
                service.getServiceName(),
                service.getServiceDetail(),
                service.getServiceQuantity(),
                service.getServicePrice(),
                service.getCategory().getId()
        );
    }
}