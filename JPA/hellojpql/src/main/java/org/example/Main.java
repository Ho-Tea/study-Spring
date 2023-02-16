package org.example;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{

            Team team = new Team();
            team.setName("teamA");

            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            member.setAge(10);
            member.setTeam(team);
            team.getMembers().add(member);
            em.persist(member);

            em.flush();
            em.clear();
            String query = "select m from Member m inner join m.team t";
            List<Member> result = em.createQuery(query, Member.class).getResultList();
//
//            List<MemberDTO> resultList = em.createQuery("select new org.example.MemberDTO(m.name, m.age) from Member m", MemberDTO.class)
//                    .getResultList();
//            MemberDTO memberDTO = resultList.get(0);
//            System.out.println("MemberDTO = " + memberDTO.getName());
//            System.out.println("MemberDTO = " + memberDTO.getAge());
//            TypedQuery<Member> query = em.createQuery("select m from Member m where m.name = : username", Member.class);
//            query.setParameter("username" , "member1");
//            Member singleResult = query.getSingleResult();
//            System.out.println("singleResult = " + singleResult);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
            emf.close();
        }
    }
}