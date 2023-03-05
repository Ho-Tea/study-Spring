package com.example.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
    @Embedded
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus status;
}
