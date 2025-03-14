package com.example.demo.DTO;

import com.example.demo.entity.SettingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingDTO {
    private String settingValue;
    private String description;
    private Timestamp lastUpdated;
    private SettingStatus status;
}
