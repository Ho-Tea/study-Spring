package org.example;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.example.jpabook.jpashop.domain.Member;
import org.example.jpabook.jpashop.domain.Movie;
import org.example.jpabook.jpashop.domain.Order;
import org.example.jpabook.jpashop.domain.OrderItem;
import org.example.jpabook.jpashop.domain.cascade.Child;
import org.example.jpabook.jpashop.domain.cascade.Parent;

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
//            em.clear();
//            em.flush();
//
//            Movie movie = new Movie();
//            movie.setName("JPA");
//            movie.setActor("actor");
//            movie.setDirector("director");
//            Member member = new Member();
//            member.setName("hi");
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            Member findMember = em.find(Member.class, member.getId());
//            Member proxyMember = em.getReference(Member.class, member.getId());
//
//            logic(findMember, proxyMember);
//            System.out.println(findMember.getId());
//            System.out.println(findMember.getName());
//
//            Child child1 = new Child();
//            Child child2 = new Child();
//
//            Parent parent = new Parent();
//            parent.addChild(child1);
//            parent.addChild(child2);
//
//            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);   cascade로 따로 안해줘도 persist된다

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }

    private static void logic(Member findMember, Member proxyMember) {
        System.out.println("findMember == proxyMember : "+ (findMember instanceof Member));
        System.out.println("findMember == proxyMember : "+ (proxyMember instanceof Member));
        System.out.println("findMember == proxyMember : "+ (proxyMember == findMember));
    }
}