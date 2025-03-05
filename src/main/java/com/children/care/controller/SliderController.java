//package com.children.care.controller;
//
//import com.children.care.entity.Slider;
//import com.children.care.service.SliderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/sliders")
//class SliderController {
//    @Autowired
//    private SliderService sliderService;
//
//    @GetMapping
//    public ResponseEntity<List<Slider>> getAllSliders() {
//        return ResponseEntity.ok(sliderService.getAllSliders());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Slider> getSliderById(@PathVariable Long id) {
//        Slider slider = sliderService.getSliderById(id);
//        return (slider != null) ? ResponseEntity.ok(slider) : ResponseEntity.notFound().build();
//    }
//
//    @PostMapping
//    public ResponseEntity<Slider> createSlider(@RequestBody Slider slider) {
//        return ResponseEntity.ok(sliderService.createSlider(slider));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Slider> updateSlider(@PathVariable Long id, @RequestBody Slider slider) {
//        Slider updatedSlider = sliderService.updateSlider(id, slider);
//        return (updatedSlider != null) ? ResponseEntity.ok(updatedSlider) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteSlider(@PathVariable Long id) {
//        return sliderService.deleteSlider(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
//    }
//}
