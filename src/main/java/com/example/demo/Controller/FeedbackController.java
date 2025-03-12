package com.example.demo.Controller;

import com.example.demo.DTO.FeedbackDTO;
import com.example.demo.Service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<String> addFeedback(@RequestBody FeedbackDTO feedbackRequest) {
        feedbackService.addFeedback(feedbackRequest);
        return ResponseEntity.ok("Feedback đã được gửi");
    }
}
