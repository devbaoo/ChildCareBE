package com.example.demo.Repository;

import com.example.demo.entity.Slider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SliderRepository extends JpaRepository<Slider, String> {
}