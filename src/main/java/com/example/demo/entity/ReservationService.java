package com.example.demo.entity;


import com.example.demo.Reservation.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ReservationService")
public class ReservationService {
    @EmbeddedId
    private ReservationServiceId id;

    @ManyToOne
    @MapsId("reservationId")
    @JoinColumn(name = "ReservationID")
    private Reservation reservation;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "ServiceID")
    private Service service;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "NoP")
    private Integer numberOfPeople;

    @Column(name = "Cost")
    private BigDecimal cost;
}