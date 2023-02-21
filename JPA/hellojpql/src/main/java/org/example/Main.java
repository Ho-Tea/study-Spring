package org.example;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{

            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member = new Member();
            member.setName("member1");
            member.setAge(10);
            member.setTeam(teamA);
            teamA.getMembers().add(member);
            em.persist(member);

            Member member2 = new Member();
            member2.setName("member2");
            member2.setAge(15);
            member2.setTeam(teamA);
            teamA.getMembers().add(member2);
            em.persist(member2);

            Member member3 = new Member();
            member3.setName("member3");
            member3.setAge(20);
            member3.setTeam(teamB);
            teamB.getMembers().add(member3);
            em.persist(member3);

            em.flush();
            em.clear();
//            String query = "select m from Member m inner join m.team t";
//            String query = "select t.members from Team t ";
//            Collection result = em.createQuery(query, Collection.class).getResultList();
//            String query = "select distinct t from Team t join fetch t.members";
            String query = "select t from Team t";
            List<Team> result = em.createQuery(query, Team.class).getResultList();
            System.out.println(result.size());
            for(Team team : result){
                System.out.println("team = " + team.getName() + "|members = " + team.getMembers().size());
                for (Member member1 : team.getMembers()){
                    System.out.println(" -> member : "+ member1);
                }
            }
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