package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

    @Id
    @Column(name = "ID", length = 255, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "CustomerID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "Feedback_ibfk_1"))
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "ServiceID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "Feedback_ibfk_2"))
    private ServiceEntity service;

    @Column(name = "FeedbackDetail", length = 255)
    private String feedbackDetail;

    @Column(name = "Rating")
    private Integer rating;

    @Column(name = "Status")
    private String status;
}
