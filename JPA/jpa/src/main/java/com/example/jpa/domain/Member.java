package com.example.jpa.domain;


import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = member, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    

}
