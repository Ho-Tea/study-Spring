package org.example.jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_Id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
    //개발하다 필요하면 양방향을 셋팅하자 양방향은 단순 조회용이다

    @ElementCollection
    @CollectionTable(name = "Favorite_Food" , joinColumns =
                @JoinColumn(name = "member_Id"))
    @Column(name = "food_Name")
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "Address", joinColumns =
//            @JoinColumn(name = "member_Id"))
//    private List<Address> addressHistory = new ArrayList<>();

    @Embedded
    private Period period;

    @Embedded
    private Address homeAddress;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "city", column = @Column(name = "Work_city")),
            @AttributeOverride(name = "street", column = @Column(name = "Work_street")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "Work_zipcode"))})
    private Address workAddress;
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
