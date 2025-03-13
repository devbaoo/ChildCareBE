package com.example.demo.Service;

import com.example.demo.DTO.SliderDTO;
import com.example.demo.entity.Slider;
import com.example.demo.Repository.SliderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SliderService {
    @Autowired
    private SliderRepository sliderRepository;

    public List<SliderDTO> getAllSliders() {
        return sliderRepository.findAll().stream().map(slider ->
                new SliderDTO(slider.getSliderId(), slider.getTitle(), slider.getImage(), slider.getBacklink())
        ).collect(Collectors.toList());
    }

    public SliderDTO getSliderDetail(String sliderId) {
        return sliderRepository.findById(sliderId)
                .map(s -> new SliderDTO(s.getSliderId(), s.getTitle(), s.getImage(), s.getBacklink()))
                .orElse(null);
    }


    // 1️⃣ Lấy danh sách Slider (filter theo status, search theo title)
    public List<SliderDTO> getSliders(String status, String title) {
        List<Slider> sliders = sliderRepository.findByStatusAndTitle(status, title);
        return sliders.stream()
                .map(s -> new SliderDTO(s.getSliderId(), s.getTitle(), s.getImage(), s.getBacklink()))
                .collect(Collectors.toList());
    }

    // 2️⃣ Ẩn/Hiện Slider (Cập nhật status)
    public boolean toggleSliderStatus(String sliderId, String newStatus) {
        Optional<Slider> sliderOpt = sliderRepository.findById(sliderId);
        if (sliderOpt.isPresent()) {
            Slider slider = sliderOpt.get();
            slider.setStatus(newStatus);
            sliderRepository.save(slider);
            return true;
        }
        return false;
    }

    // 3️⃣ Chỉnh sửa Slider
    public boolean updateSlider(SliderDTO sliderDTO) {
        Optional<Slider> sliderOpt = sliderRepository.findById(sliderDTO.getSliderId());
        if (sliderOpt.isPresent()) {
            Slider slider = sliderOpt.get();
            slider.setTitle(sliderDTO.getTitle());
            slider.setImage(sliderDTO.getImage());
            slider.setBacklink(sliderDTO.getBacklink());
            sliderRepository.save(slider);
            return true;
        }
        return false;
    }
}
