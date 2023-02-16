package org.example;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
public class Order {
    @Id @GeneratedValue
    private Long id;
    private int orderAmount;

    @ManyToOne
    @JoinColumn(name = "member_Id")
    private Member member;

    @Embedded
    private Address address;

}
