package com.example.demo.Service;

import com.example.demo.DTO.SliderDTO;
import com.example.demo.entity.Slider;
import com.example.demo.Repository.SliderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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
}
