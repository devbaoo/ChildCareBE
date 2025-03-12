package com.example.demo.Service;

import com.example.demo.DTO.FeedbackDTO;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.FeedbackRepository;
import com.example.demo.Repository.ServiceRepository;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Feedback;
import com.example.demo.entity.ServiceEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final CustomerRepository customerRepository;
    private final ServiceRepository serviceRepository;

    @Transactional
    public void addFeedback(FeedbackDTO feedbackRequest) {
        Customer customer = customerRepository.findById(feedbackRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Khách hàng không tồn tại"));
        ServiceEntity serviceEntity = serviceRepository.findById(feedbackRequest.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("Dịch vụ không tồn tại"));

        Feedback feedback = new Feedback();
        feedback.setId(UUID.randomUUID().toString());
        feedback.setCustomer(customer);
        feedback.setService(serviceEntity);
        feedback.setFeedbackDetail(feedbackRequest.getFeedbackDetail());
        feedback.setRating(feedbackRequest.getRating());

        feedbackRepository.save(feedback);
    }
}
