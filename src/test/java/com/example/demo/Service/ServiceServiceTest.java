package com.example.demo.Service;

import com.example.demo.DTO.CreateServiceDTO;
import com.example.demo.DTO.ServiceDTO;
import com.example.demo.Mapper.ServiceMapper;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ServiceRepository;
import com.example.demo.entity.Category;
import com.example.demo.entity.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ServiceMapper serviceMapper;

    @InjectMocks
    private ServiceService serviceService;

    private Service service;
    private ServiceDTO serviceDTO;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId("1");

        service = new Service();
        service.setId("123");
        service.setServiceName("Test Service");
        service.setServiceDetail("Service Details");
        service.setServiceQuantity(10);
        service.setServicePrice(BigDecimal.valueOf(100));
        service.setCategory(category);

        serviceDTO = new ServiceDTO("123", "Test Service", "Service Details", 10, BigDecimal.valueOf(100), "1");
    }

    @Test
    void testGetAllServices() {
        Page<Service> services = new PageImpl<>(List.of(service));
        when(serviceRepository.findAll(PageRequest.of(0, 10))).thenReturn(services);
        doReturn(serviceDTO).when(serviceMapper).toDTO(any(Service.class));


        Page<ServiceDTO> result = serviceService.getAllServices(0, 10);

        assertEquals(1, result.getContent().size());
        assertEquals("Test Service", result.getContent().get(0).getServiceName());
    }

    @Test
    void testGetServiceById_Found() {
        when(serviceRepository.findById("123")).thenReturn(Optional.of(service));
        doReturn(serviceDTO).when(serviceMapper).toDTO(any(Service.class));

        Optional<ServiceDTO> result = serviceService.getServiceById("123");

        assertTrue(result.isPresent());
        assertEquals("Test Service", result.get().getServiceName());
    }

    @Test
    void testGetServiceById_NotFound() {
        when(serviceRepository.findById("999")).thenReturn(Optional.empty());

        Optional<ServiceDTO> result = serviceService.getServiceById("999");

        assertFalse(result.isPresent());
    }

    @Test
    void testCreateService() {
        CreateServiceDTO createServiceDTO = new CreateServiceDTO("Test Service", "Service Details", 10, BigDecimal.valueOf(100), "1");
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));
        when(serviceRepository.save(any(Service.class))).thenReturn(service);
        doReturn(serviceDTO).when(serviceMapper).toDTO(any(Service.class));

        ServiceDTO result = serviceService.createService(createServiceDTO);

        assertNotNull(result);
        assertEquals("Test Service", result.getServiceName());
    }

    @Test
    void testUpdateService() {
        CreateServiceDTO updateDTO = new CreateServiceDTO("Updated Service", "Updated Details", 5, BigDecimal.valueOf(50), "1");
        when(serviceRepository.findById("123")).thenReturn(Optional.of(service));
        when(serviceRepository.save(any(Service.class))).thenReturn(service);
        when(serviceMapper.toDTO(service)).thenReturn(new ServiceDTO("123", "Updated Service", "Updated Details", 5, BigDecimal.valueOf(50), "1"));

        ServiceDTO result = serviceService.updateService("123", updateDTO);

        assertEquals("Updated Service", result.getServiceName());
        assertEquals(5, result.getServiceQuantity());
    }

    @Test
    void testHideService() {
        when(serviceRepository.findById("123")).thenReturn(Optional.of(service));
        serviceService.hideService("123");
        assertEquals(0, service.getServiceQuantity());
        verify(serviceRepository, times(1)).save(service);
    }

    @Test
    void testShowService() {
        when(serviceRepository.findById("123")).thenReturn(Optional.of(service));
        serviceService.showService("123", 20);
        assertEquals(20, service.getServiceQuantity());
        verify(serviceRepository, times(1)).save(service);
    }
}
