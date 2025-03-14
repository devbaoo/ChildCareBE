package com.example.demo.Controller;

import com.example.demo.DTO.FeedbackDTO;
import com.example.demo.Service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<String> addFeedback(@RequestBody FeedbackDTO feedbackRequest) {
        feedbackService.addFeedback(feedbackRequest);
        return ResponseEntity.ok("Feedback đã được gửi");
    }

    @GetMapping
    public ResponseEntity<Page<FeedbackDTO>> getAllFeedbacks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks(search, status, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable String id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateFeedbackStatus(@PathVariable String id,
                                                     @RequestParam String status) {
        feedbackService.updateFeedbackStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteFeedback(@PathVariable String id) {
        feedbackService.softDeleteFeedback(id);
        return ResponseEntity.ok().build();
    }
}
