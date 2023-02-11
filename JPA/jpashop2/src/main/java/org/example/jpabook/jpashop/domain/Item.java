package org.example.jpabook.jpashop.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//
//    @OneToMany(mappedBy = "item")
//    private List<OrderItem> orderItems = new ArrayList<>();
// 중요한게 아니다 상품을 보고 이 상품을 어떤 주문으로 팔렸는지 중요하지는 않으므로

    @ManyToMany(mappedBy = "items")
    List<Category> categories = new ArrayList<>();
    private String name;
    private int price;
    private int stockQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
