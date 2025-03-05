package com.example.demo.homepage;

import com.example.demo.entity.Post;
import com.example.demo.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomepageController {

    @Autowired
    private SliderRepo sliderRepository;

    @Autowired
    private PostRepo postRepository;

    @Autowired
    private ServiceRepo serviceRepository;

    @GetMapping("/sliders")
    public List<Slider> getSliders() {
        return sliderRepository.findAll();
    }

    @GetMapping("/hot-posts")
    public List<Post> getHotPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/featured-services")
    public List<Service> getFeaturedServices() {
        return serviceRepository.findAll();
    }

    @GetMapping("/latest-posts")
    public List<Post> getLatestPosts() {
        return postRepository.findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"))).getContent();
    }

    @GetMapping("/contacts")
    public Map<String, String> getContacts() {
        Map<String, String> contacts = new HashMap<>();
        contacts.put("email", "contact@example.com");
        contacts.put("phone", "+123456789");
        contacts.put("address", "123 Main Street, City");
        return contacts;
    }
}

