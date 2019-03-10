package com.acasssta.chooseCats.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="cats")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="photo")
    @Lob
    private byte[] photo;

    @Column(name = "rating")
    private Integer rating;

    @ManyToMany
    @JoinTable(
            name = "user_cat",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    private List<Cat> catUser;

    public Cat() {
    }
}
