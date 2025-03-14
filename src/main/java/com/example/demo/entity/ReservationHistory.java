package com.example.demo.entity;

import com.example.demo.Reservation.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ReservationHistory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationHistory {

    @EmbeddedId
    private ReservationHistoryId id;

    @ManyToOne
    @JoinColumn(name = "ReservationID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "CustomerID", referencedColumnName = "ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "StaffID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Staff staff;

    private String changes;

    @Column(name = "ChangeTime", insertable = false, updatable = false)
    private LocalDateTime changeTime;
}