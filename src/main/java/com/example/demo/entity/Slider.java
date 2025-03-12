package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Slider")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Slider {

    @Id
    @Column(name = "\"SliderID\"", columnDefinition = "VARCHAR(255) NOT NULL")
    private String sliderId;

    @Column(name = "Title", length = 255)
    private String title;

    @Column(name = "Image", length = 255)
    private String image;

    @Column(name = "Backlink", length = 255)
    private String backlink;

    @Column(name = "Status", length = 50)
    private String status;

    @Column(name = "Notes", length = 255)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "CategoryID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "Slider_ibfk_1"))
    private Category category;

    @ManyToOne
    @JoinColumn(name = "PostID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "Slider_ibfk_2"))
    private Post post;
}