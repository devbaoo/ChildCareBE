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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        feedback.setStatus("ACTIVE");

        feedbackRepository.save(feedback);
    }

    public Page<FeedbackDTO> getAllFeedbacks(String search, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return feedbackRepository.searchFeedbacks(search, status, pageable)
                .map(this::convertToDTO);
    }

    public FeedbackDTO getFeedbackById(String id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
        return convertToDTO(feedback);
    }

    public void updateFeedbackStatus(String id, String status) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
        feedback.setStatus(status);
        feedbackRepository.save(feedback);
    }

    public void softDeleteFeedback(String id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
        feedback.setStatus("INACTIVE");
        feedbackRepository.save(feedback);
    }

    private FeedbackDTO convertToDTO(Feedback feedback) {
        return new FeedbackDTO(
                feedback.getCustomer().getId(),
                feedback.getService().getId(),
                feedback.getFeedbackDetail(),
                feedback.getRating(),
                feedback.getStatus()
        );
    }
}
