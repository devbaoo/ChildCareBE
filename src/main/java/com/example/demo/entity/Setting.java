package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "Setting")
@Data
public class Setting {

    @Id
    @Column(name = "ID", length = 255, nullable = false)
    private String id;

    @Column(name = "SettingValue", columnDefinition = "TEXT")
    private String settingValue;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "LastUpdated")
    private Timestamp lastUpdated;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private SettingStatus status;
}