package com.example.demo.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class ReservationServiceId implements Serializable {
    private String reservationId;
    private String serviceId;
}
