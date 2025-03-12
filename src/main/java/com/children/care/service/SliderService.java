//package com.children.care.service;
//
//import com.children.care.entity.Slider;
//import com.children.care.repository.SliderRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class SliderService {
////    @Autowired
////    private SliderRepository sliderRepository;
////
////    public List<Slider> getAllSliders() {
////        return sliderRepository.findAll();
////    }
////
////    public Slider getSliderById(Long id) {
////        return sliderRepository.findById(id).orElse(null);
////    }
////
////    public Slider createSlider(Slider slider) {
////        return sliderRepository.save(slider);
////    }
////
////    public Slider updateSlider(Long id, Slider updatedSlider) {
////        return sliderRepository.findById(id).map(slider -> {
////            slider.setTitle(updatedSlider.getTitle());
////            slider.setImage(updatedSlider.getImage());
////            slider.setBacklink(updatedSlider.getBacklink());
////            slider.setStatus(updatedSlider.getStatus());
////            slider.setNotes(updatedSlider.getNotes());
////            slider.setCategoryId(updatedSlider.getCategoryId());
////            slider.setPostId(updatedSlider.getPostId());
////            return sliderRepository.save(slider);
////        }).orElse(null);
////    }
////
////    public boolean deleteSlider(Long id) {
////        if (sliderRepository.existsById(id)) {
////            sliderRepository.deleteById(id);
////            return true;
////        }
////        return false;
////    }
//}
