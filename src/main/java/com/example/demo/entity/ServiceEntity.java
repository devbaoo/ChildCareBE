package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Service")
public class ServiceEntity {
    @Id
    @Column(name = "ID", length = 255)
    private String id;

    @Column(name = "ServiceName")
    private String serviceName;

    @Column(name = "ServiceDetail")
    private String serviceDetail;

    @Column(name = "ServiceQuantity")
    private Integer serviceQuantity;

    @Column(name = "ServicePrice", updatable = true, insertable = true)
    private BigDecimal servicePrice;

    @ManyToOne
    @JoinColumn(name = "StaffID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "Service_ibfk_1"))
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category category;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReservationService> reservationServices;
}