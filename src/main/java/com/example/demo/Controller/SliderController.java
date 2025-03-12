package com.example.demo.Controller;

import com.example.demo.DTO.SliderDTO;
import com.example.demo.Service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sliders")
public class SliderController {
    @Autowired
    private SliderService sliderService;

    @GetMapping
    public List<SliderDTO> getAllSliders() {
        return sliderService.getAllSliders();
    }
}