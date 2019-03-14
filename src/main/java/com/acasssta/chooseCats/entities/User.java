package com.acasssta.chooseCats.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    private String role;

    @ManyToMany
    @JoinTable(
            name = "user_cat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id")
    )
    private List<Cat> userCat;

    public User() {
    }

}
