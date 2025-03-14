package com.example.demo.Service;

import com.example.demo.DTO.SettingDTO;
import com.example.demo.Repository.SettingRepository;
import com.example.demo.entity.Feedback;
import com.example.demo.entity.Setting;
import com.example.demo.entity.SettingStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;

    public Page<SettingDTO> getSettings(int page, int size, SettingStatus status, String keyword, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return settingRepository.findAllWithFilters( status, keyword, pageable)
                .map(setting -> new SettingDTO(setting.getSettingValue(), setting.getDescription(), setting.getLastUpdated(), setting.getStatus()));
    }

    public SettingDTO getSettingByKey(String id) {
        Setting setting = settingRepository.findById(id)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Setting không tồn tại"));
        return new SettingDTO(setting.getSettingValue(), setting.getDescription(), setting.getLastUpdated(), setting.getStatus());
    }

    public SettingDTO createSetting(SettingDTO settingDTO) {
        Setting setting = new Setting();
        setting.setId(UUID.randomUUID().toString());
        setting.setSettingValue(settingDTO.getSettingValue());
        setting.setDescription(settingDTO.getDescription());
        setting.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        setting.setStatus(settingDTO.getStatus());

        settingRepository.save(setting);
        return settingDTO;
    }

    public SettingDTO updateSetting(String id, SettingDTO settingDTO) {
        Setting setting = settingRepository.findById(id)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Setting không tồn tại"));

        setting.setSettingValue(settingDTO.getSettingValue());
        setting.setDescription(settingDTO.getDescription());
        setting.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        setting.setStatus(settingDTO.getStatus());

        settingRepository.save(setting);
        return settingDTO;
    }

    public void softDeleteSetting(String id) {
        Setting setting = settingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setting not found"));
        setting.setStatus(SettingStatus.valueOf("INACTIVE"));
        settingRepository.save(setting);
    }



}
