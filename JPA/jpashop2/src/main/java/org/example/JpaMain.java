package org.example;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.example.jpabook.jpashop.domain.Order;
import org.example.jpabook.jpashop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em  = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
//            Order order = new Order();
//            em.persist(order);
//            order.addOrderItem(new OrderItem());

//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);  //단방향인 경우만으로도 어플리케이션 개발은 문제가 없다
//            em.persist(orderItem);
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}