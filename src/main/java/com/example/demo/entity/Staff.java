package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Staff")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {

    @Id
    @Column(name = "ID", length = 255, nullable = false)
    private String id;

    @OneToOne
    @JoinColumn(name = "AccountId", referencedColumnName = "ID", unique = true, foreignKey = @ForeignKey(name = "Staff_ibfk_1"))
    private Account account;

    @Column(name = "Specialization", length = 255)
    private String specialization;
}