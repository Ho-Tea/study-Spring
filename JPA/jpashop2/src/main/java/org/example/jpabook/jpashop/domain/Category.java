package org.example.jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "Parent_Id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child;

//    @ManyToMany
//    @JoinTable(name = "Category_Item",
//            joinColumns = @JoinColumn(name = "Category_Id"),
//            inverseJoinColumns = @JoinColumn(name = "Item_Id"))
//    private List<Item> items = new ArrayList<>();

}
