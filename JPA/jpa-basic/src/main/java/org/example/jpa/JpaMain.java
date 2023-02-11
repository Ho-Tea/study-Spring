package org.example.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        try{
//            Member member1 = new Member(1L, "hss",23);
//            em.persist(member1);
//            et.commit();
            Member member = new Member();
            member.setUsername("hi");
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("setName");
            em.persist(member);
            et.commit();
//            List<Member> result = em.createQuery("select m from Member as m",Member.class)
//                    .setMaxResults(10)
//                    .getResultList();
//            for(Member member : result){
//                System.out.println(member.getName());
//            }
//
//            Member member1 = new Member(15L, "A");
//            Member member2 = new Member(20L, "B");
//
//            em.persist(member1);
//            em.persist(member2);


        }catch(Exception e){
            et.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
